package com.project.salesmanagement.services.vnpay;

import com.project.salesmanagement.dtos.payment.PaymentDTO;
import com.project.salesmanagement.dtos.payment.PaymentQueryDTO;
import com.project.salesmanagement.dtos.payment.PaymentRefundDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface IVNPayService {
    String createPaymentUrl(PaymentDTO paymentRequest, HttpServletRequest request);
    String queryTransaction(PaymentQueryDTO paymentQueryDTO, HttpServletRequest request) throws IOException;
    String refundTransaction(PaymentRefundDTO refundDTO) throws IOException;
}
