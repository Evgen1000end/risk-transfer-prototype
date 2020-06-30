package com.moex.esb.risktransfer.services;

import com.moex.esb.risktransfer.model.AstsTrade;
import com.moex.esb.risktransfer.model.EnrichedRiskTransferRequest;
import com.moex.esb.risktransfer.model.PostCommitResponse;
import com.moex.esb.risktransfer.model.PrecommitResponse;
import com.moex.esb.risktransfer.model.RiskTransferRequest;
import com.moex.esb.risktransfer.model.RollbackResponse;
import com.moex.esb.risktransfer.model.SpectraDeal;
import org.springframework.stereotype.Service;

@Service
public class SpectraGateService {

  public PrecommitResponse precommit(EnrichedRiskTransferRequest request) {
    return new PrecommitResponse();
  }

  public PostCommitResponse postCommit(EnrichedRiskTransferRequest request) {
    return new PostCommitResponse();
  }

  public RollbackResponse rollback(Throwable t, RiskTransferRequest request) {
    return new RollbackResponse();
  }

  public SpectraDeal findDealByOrderNo(String orderNo) {
    return new SpectraDeal();
  }
}
