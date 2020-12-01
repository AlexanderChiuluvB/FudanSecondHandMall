package com.fduexchange.dao;

import com.fduexchange.pojo.ShopMessage;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShopMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopMessage record);

    int insertSelective(ShopMessage record);

    ShopMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopMessage record);

    int updateByPrimaryKey(ShopMessage record);

    int getCounts(int sid);

    List<ShopMessage> findById(int sid, int start);

    @Select("select * from ShopMessage where sid=#{id,jdbcType=INTEGER} and display=1")
    List<ShopMessage> selectBySid(int id);
}