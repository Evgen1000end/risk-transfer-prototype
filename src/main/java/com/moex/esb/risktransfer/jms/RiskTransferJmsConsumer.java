package com.moex.esb.risktransfer.jms;

import com.moex.esb.risktransfer.model.RiskTransferRequest;
import com.moex.esb.risktransfer.model.RiskTransferResponse;
import com.moex.esb.risktransfer.services.Protobufer;
import com.moex.esb.risktransfer.services.RiskTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

public class RiskTransferJmsConsumer {
  @Autowired
  private JmsTemplate jms;
  @Value("${app.destinations.response}")
  private String queue;
  @Autowired
  private RiskTransfer riskTransfer;
  @Autowired
  private Protobufer protobuf;

  @JmsListener(destination = "${app.destinations.request}")
  public void popMessage(BytesMessage message) {
    try {
      final byte[] requestPayload = processRequest(message);
      final RiskTransferRequest request = protobuf.request(requestPayload);
      final RiskTransferResponse response = riskTransfer.execute(request);
      final byte[] responsePayload = protobuf.response(response);
      sendResponse(responsePayload);
    } catch (Throwable t) {
      final RiskTransferResponse errorResponse = RiskTransferResponse.error(t);
      final byte[] errorPayload = protobuf.response(errorResponse);
      sendResponse(errorPayload);
    }
  }

  private byte[] processRequest(BytesMessage message) throws JMSException {
    final byte[] payload = new byte[(int) message.getBodyLength()];
    message.readBytes(payload);
    return payload;
  }

  private void sendResponse(byte[] payload) {
    jms.send(queue, session -> {
      final BytesMessage bytesMessage = session.createBytesMessage();
      bytesMessage.writeBytes(payload);
      return bytesMessage;
    });
  }

}
