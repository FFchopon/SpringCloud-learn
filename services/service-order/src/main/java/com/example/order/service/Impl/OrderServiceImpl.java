package com.example.order.service.Impl;
import java.math.BigDecimal;

import com.example.order.service.OrderService;
import com.example.order.bean.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Long productId, Long userId) {
        Order order = new Order();

        order.setId(1L);
        // TODO 总金额
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("zhangsan");
        order.setAddress("天津大学");
        // TODO 远程查询商品列表
        order.setProductList(null);

        return order;
    }
}
