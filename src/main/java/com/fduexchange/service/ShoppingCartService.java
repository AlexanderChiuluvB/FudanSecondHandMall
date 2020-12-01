package com.fduexchange.service;

import com.fduexchange.pojo.ShoppingCart;

import java.util.List;


public interface ShoppingCartService {
    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);

    List<ShoppingCart> selectByUid(int uid);
}
