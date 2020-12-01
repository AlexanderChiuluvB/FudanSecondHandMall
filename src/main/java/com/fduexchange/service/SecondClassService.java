package com.fduexchange.service;

import com.fduexchange.pojo.SecondClass;

import java.util.List;


public interface SecondClassService {
    int deleteByPrimaryKey(Integer id);

    int insert(SecondClass record);

    int insertSelective(SecondClass record);

    SecondClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecondClass record);

    int updateByPrimaryKey(SecondClass record);

    List<SecondClass> selectByAid(int aid);
}
