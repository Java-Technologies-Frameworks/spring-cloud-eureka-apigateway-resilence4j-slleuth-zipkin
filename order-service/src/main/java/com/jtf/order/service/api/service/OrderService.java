package com.jtf.order.service.api.service;

import com.jtf.order.service.api.common.Payment;
import com.jtf.order.service.api.common.TransactionRequest;
import com.jtf.order.service.api.common.TransactionResponse;
import com.jtf.order.service.api.entity.Order;
import com.jtf.order.service.api.repository.OrderRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@RefreshScope
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL;
    private int attempt = 1;

    //@CircuitBreaker(name = "ORDER-SERVICE",fallbackMethod = "paymentDoneAndOrderPlaced")
    @Retry(name = "ORDER-SERVICE", fallbackMethod = "paymentDoneAndOrderPlaced")
    public TransactionResponse saveOrder(TransactionRequest request) {

        Payment paymentResponse = null;
        String resMessage = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        try {
            logger.info("Transaction request details :: " + request.getOrder());
            //rest call
            //Payment paymentResponse = restTemplate.postForObject("http://localhost:9191/payment/doPayment", payment, Payment.class);
            System.out.println("retry method called " + attempt++ + " times " + " at " + new Date());
            logger.info("Before calling to payment service API " + "api end point url :: http://PAYMENT-SERVICE/payment/doPayment" + "request details " + payment);
            /*instaed of mapping the host and port map the service name */
            paymentResponse = restTemplate.postForObject(PAYMENT_ENDPOINT_URL, payment, Payment.class);
            logger.info("payment API response from payment service .." + paymentResponse);
            resMessage = paymentResponse.getPaymentStatus().equals("success") ? "payment processing is sucessful and order placed" : "there is a failure in payment API order added to cart";
            orderRepository.save(order);
            logger.info("Order details inserted into order table :: " + order);

        } catch (Exception ee) {
            logger.error("Exception occurred in saveOrder Service method " + ee);
            return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getAmount(), resMessage);
        }
        return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getAmount(), resMessage);
    }


    //fallback method
    public TransactionResponse paymentDoneAndOrderPlaced(Exception e) {
        logger.info("Entry of fallback method due to payment service down and return default fallback messsage");
        return new TransactionResponse(new Order(999, "Order placed", 1, 15000), "TNX12345", 15000, "payment processing is sucessful and order placed");
    }
}
