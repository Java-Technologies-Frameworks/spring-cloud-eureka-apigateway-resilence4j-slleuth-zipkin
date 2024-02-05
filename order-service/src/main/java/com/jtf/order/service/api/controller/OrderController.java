package com.jtf.order.service.api.controller;

import com.jtf.order.service.api.common.TransactionRequest;
import com.jtf.order.service.api.common.TransactionResponse;
import com.jtf.order.service.api.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) {
        logger.info("Order request details :"+request.getOrder()+ "  " +request.getPayment());
        return orderService.saveOrder(request);
    }
}
