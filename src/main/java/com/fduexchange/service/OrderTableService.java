package com.fduexchange.service;

import com.fduexchange.pojo.OrderTable;

import java.util.List;


public interface OrderTableService {

    int insertSelective(OrderTable record);

    int insert(OrderTable record);

    List<OrderTable> selectBySellerId(int seller_id);

    List<OrderTable> selectByPurchaserId(int purchaser_id);

    int updateState(OrderTable record);
}
