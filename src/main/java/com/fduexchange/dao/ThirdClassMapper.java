package com.fduexchange.dao;

import com.fduexchange.pojo.ThirdClass;

import java.util.List;

public interface ThirdClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThirdClass record);

    int insertSelective(ThirdClass record);

    ThirdClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdClass record);

    int updateByPrimaryKey(ThirdClass record);

    List<ThirdClass> selectByCid(int cid);
}