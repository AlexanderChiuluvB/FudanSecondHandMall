package com.fduexchange.service.Impl;

import com.fduexchange.dao.FirstClassMapper;
import com.fduexchange.pojo.FirstClass;
import com.fduexchange.service.FirstClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class FirstClassServiceImpl implements FirstClassService {
    @Resource
    private FirstClassMapper firstClassMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(FirstClass record) {
        return firstClassMapper.insert(record);
    }

    @Override
    public int insertSelective(FirstClass record) {
        return firstClassMapper.insertSelective(record);
    }

    @Override
    public FirstClass selectByPrimaryKey(Integer id) {
        return firstClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(FirstClass record) {
        return firstClassMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FirstClass record) {
        return firstClassMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<FirstClass> selectAll() {
        return firstClassMapper.selectAll();
    }
}
