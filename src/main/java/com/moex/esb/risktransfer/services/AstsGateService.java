package com.moex.esb.risktransfer.services;

import com.moex.esb.risktransfer.model.AstsCommandResponse;
import com.moex.esb.risktransfer.model.AstsTrade;
import com.moex.esb.risktransfer.model.EnrichedRiskTransferRequest;
import org.springframework.stereotype.Service;

@Service
public class AstsGateService {

  public AstsCommandResponse riskTransfer(EnrichedRiskTransferRequest request) {
    return new AstsCommandResponse();
  }

  public AstsTrade findTradeByBrokerRef(String brokerRef) {
    return new AstsTrade();
  }

}
