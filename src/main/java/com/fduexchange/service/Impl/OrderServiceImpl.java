package com.fduexchange.service.Impl;

import com.fduexchange.dao.AllSalesMapper;
import com.fduexchange.dao.OrderMapper;
import com.fduexchange.pojo.Order;
import com.fduexchange.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper OrderMapper;

    @Override
    public int insert(Order record) {
        return OrderMapper.insert(record);
    }

    @Override
    public List<Order> selectBySellerId(int seller_id){
        return OrderMapper.selectBySellerId(seller_id);
    }

    @Override
    public List<Order> selectByPurchaserId(int purchaser_id) {
        return OrderMapper.selectByPurchaserId(purchaser_id);
    }
}
