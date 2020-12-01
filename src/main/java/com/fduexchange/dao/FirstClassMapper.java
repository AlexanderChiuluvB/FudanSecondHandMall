package com.fduexchange.dao;

import com.fduexchange.pojo.FirstClass;

import java.util.List;

public interface FirstClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FirstClass record);

    int insertSelective(FirstClass record);

    FirstClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FirstClass record);

    int updateByPrimaryKey(FirstClass record);

    List<FirstClass> selectAll();
}