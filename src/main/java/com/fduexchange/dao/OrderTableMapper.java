package com.fduexchange.dao;

import com.fduexchange.pojo.OrderTable;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderTableMapper {

    int insert(OrderTable record);

    @Select("select * from OrderTable where seller_id=#{seller_id}")
    List<OrderTable> selectBySellerId(int seller_id);

    @Select("select * from OrderTable where purchaser_id=#{purchaser_id}")
    List<OrderTable> selectByPurchaserId(int purchaser_id);

    int updateState(OrderTable record);

    int insertSelective(OrderTable record);
}