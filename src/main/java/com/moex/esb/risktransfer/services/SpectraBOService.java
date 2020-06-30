package com.moex.esb.risktransfer.services;

import com.moex.esb.risktransfer.model.EnrichedRiskTransferRequest;
import com.moex.esb.risktransfer.model.RiskTransferRequest;
import org.springframework.stereotype.Service;

@Service
public class SpectraBOService {

  public EnrichedRiskTransferRequest enrichWithNccCodes(RiskTransferRequest request) {
    return new EnrichedRiskTransferRequest(request);
  }

  public void alert(Throwable t, RiskTransferRequest request) {

  }

}
