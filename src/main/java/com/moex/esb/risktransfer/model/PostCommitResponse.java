package com.moex.esb.risktransfer.model;

public class PostCommitResponse {
  private String orderNo;
  private int code;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
}
