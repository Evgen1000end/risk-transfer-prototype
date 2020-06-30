package com.moex.esb.risktransfer.services;

import com.moex.esb.risktransfer.model.AstsCommandResponse;
import com.moex.esb.risktransfer.model.AstsTrade;
import com.moex.esb.risktransfer.model.EnrichedRiskTransferRequest;
import com.moex.esb.risktransfer.model.PostCommitResponse;
import com.moex.esb.risktransfer.model.PrecommitResponse;
import com.moex.esb.risktransfer.model.RiskTransferRequest;
import com.moex.esb.risktransfer.model.RiskTransferResponse;
import com.moex.esb.risktransfer.model.SpectraDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskTransfer {
  @Autowired
  private SpectraBOService spectraBO;
  @Autowired
  private SpectraGateService spectraGate;
  @Autowired
  private AstsGateService astsGate;

  public RiskTransferResponse execute(RiskTransferRequest request) {
    try {
      //1. Обогощаем запрос данным по nccClientCodes из БД спектры
      final EnrichedRiskTransferRequest extendedRequest = spectraBO.enrichWithNccCodes(request);
      //2 Выполняем прекоммит в спектру
      final PrecommitResponse precommitResponse = spectraGate.precommit(extendedRequest);
      //3. Выполняем проброс риска в  ASTS
      final AstsCommandResponse astsCommandResponse = astsGate.riskTransfer(extendedRequest);

      if (astsCommandResponse.isSuccess()) {
        //4. Подпписываемся на trade
        final AstsTrade trade = astsGate.findTradeByBrokerRef(request.getBrokerRef());
        //5. Выполняем второй коммит в спектру
        final PostCommitResponse postCommitResponse = spectraGate.postCommit(extendedRequest);
        // 6. Ищем сделки
        final SpectraDeal deal = spectraGate.findDealByOrderNo(postCommitResponse.getOrderNo());
        return new RiskTransferResponse(deal.getDealId(), trade.getTradeNo());
      } else {
        // Если произошел отказ - откат в спектровый шлюз
        spectraGate.rollback(request);
        // Алертинг в спектровый бэкофис
        spectraBO.alert(request);
        return RiskTransferResponse.error();
      }
    } catch (Throwable t) {
      // Алертинг в спектровый бэкофис
      spectraBO.alert(t, request);
      return RiskTransferResponse.error(t);
    }
  }
}
