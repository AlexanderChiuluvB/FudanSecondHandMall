package com.fduexchange.service;

import com.fduexchange.pojo.ShopMessage;

import java.util.List;

public interface ShopMessageService {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopMessage record);

    int insertSelective(ShopMessage record);

    ShopMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopMessage record);

    int updateByPrimaryKey(ShopMessage record);

    int getCounts(int sid);

    List<ShopMessage> findById(int sid, int start);

    List<ShopMessage> selectById(int id);
}
