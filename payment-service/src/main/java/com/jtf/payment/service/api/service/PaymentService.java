package com.jtf.payment.service.api.service;

import com.jtf.payment.service.api.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jtf.payment.service.api.entity.Payment;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private PaymentRepository paymentRepository;

    // 2nd scenarios
    public Payment doPayment(Payment payment) {
        logger.info("Request received in doPayment method of Payment Service class :: " + payment);
        payment.setPaymentStatus(paymentProcessing());
        logger.info("payment status :: " + payment.getPaymentStatus());
        payment.setTransactionId(UUID.randomUUID().toString());
        logger.info("Transaction id created for the order id :: " + payment.getOrderId() + "  " + payment.getTransactionId());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {
        logger.info("paymentProcessing started ::");
        //api should be 3rd party payment gateway
        logger.info("paymentProcessing done ::");
        return new Random().nextBoolean() ? "success" : "false";
    }

    public Exception paymentProcessingForResilienceTest() {
        //api should be 3rd party payment gateway
        return new Exception("payment processing is fail due to low network connectivity");
    }

    //after gateway impl
    public Payment findPaymentHistoryByOrderId(int orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
