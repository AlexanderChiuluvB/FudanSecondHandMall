package com.fduexchange.dao;

import com.fduexchange.pojo.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    int insert(Order record);

    @Select("select * from Order where seller_id=#{seller_id}")
    List<Order> selectBySellerId(int seller_id);

    @Select("select * from Order where purchaser_id=#{purchaser_id}")
    List<Order> selectByPurchaserId(int purchaser_id);
}