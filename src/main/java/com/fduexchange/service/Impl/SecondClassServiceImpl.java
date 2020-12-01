package com.fduexchange.service.Impl;

import com.fduexchange.dao.SecondClassMapper;
import com.fduexchange.pojo.SecondClass;
import com.fduexchange.service.SecondClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SecondClassServiceImpl implements SecondClassService {

    @Resource
    private SecondClassMapper secondClassMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(SecondClass record) {
        return secondClassMapper.insert(record);
    }

    @Override
    public int insertSelective(SecondClass record) {
        return secondClassMapper.insertSelective(record);
    }

    @Override
    public SecondClass selectByPrimaryKey(Integer id) {
        return secondClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SecondClass record) {
        return secondClassMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SecondClass record) {
        return secondClassMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SecondClass> selectByAid(int aid) {
        return secondClassMapper.selectByAid(aid);
    }
}
