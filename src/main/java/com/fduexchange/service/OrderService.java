package com.fduexchange.service;

import com.fduexchange.pojo.Order;

import java.util.List;
import java.util.Map;


public interface OrderService {
    int insert(Order record);

    List<Order> selectBySellerId(int seller_id);

    List<Order> selectByPurchaserId(int purchaser_id);
}
