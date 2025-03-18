package com.example.order.service.Impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.example.order.service.OrderService;
import com.example.order.bean.Order;
import com.example.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    public OrderServiceImpl(@Qualifier("compositeDiscoveryClient") DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = getProductFromRemoteWithLoadBalance(productId);
        Order order = new Order();
        order.setId(1L);
        // 总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("zhangsan");
        order.setAddress("天津大学");
        // 远程查询商品列表
        order.setProductList(Arrays.asList(product));

        return order;
    }

    private Product getProductFromRemote(Long productId){
        // 1. 获取到商品服务所在的所有机器IP+Port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");

        ServiceInstance Instance = instances.get(0);

        // 远程URL
        String url = "http://" + Instance.getHost() + ":" + Instance.getPort() + "/product/"+productId;
        log.info("远程请求：{}", url);
        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return null;
    }

    // 进阶2：完成负载均衡发送请求
    private Product getProductFromRemoteWithLoadBalance(Long productId){
        // 1. 获取到商品服务所在的所有机器IP+Port
        ServiceInstance choose = loadBalancerClient.choose("service-product");

        // 远程URL
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/"+productId;
        log.info("远程请求：{}", url);
        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return null;
    }

    // 进阶3：基于注解的负载均衡
    private Product getProductFromRemoteWithLoadBalanceAnnotation(Long productId){
        String url = "http://service-product/product/"+productId;
        // 2. 给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return null;
    }
}
