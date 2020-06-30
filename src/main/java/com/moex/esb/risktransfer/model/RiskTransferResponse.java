package com.moex.esb.risktransfer.model;

public class RiskTransferResponse {

  private String spectraDeal;
  private String astsDeal;
  private int code;

  public RiskTransferResponse(String spectraDeal, String astsDeal) {
    this.spectraDeal = spectraDeal;
    this.astsDeal = astsDeal;
    this.code =0;
  }

  public RiskTransferResponse(int code) {
    this.code = code;
  }

  public static RiskTransferResponse error(Throwable t) {
    return new RiskTransferResponse(10000);
  }
}
