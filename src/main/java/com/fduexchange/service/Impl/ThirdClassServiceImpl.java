package com.fduexchange.service.Impl;

import com.fduexchange.dao.ThirdClassMapper;
import com.fduexchange.pojo.ThirdClass;
import com.fduexchange.service.ThirdClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ThirdClassServiceImpl implements ThirdClassService {
    @Resource
    private ThirdClassMapper thirdClassMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ThirdClass record) {
        return thirdClassMapper.insert(record);
    }

    @Override
    public int insertSelective(ThirdClass record) {
        return thirdClassMapper.insertSelective(record);
    }

    @Override
    public ThirdClass selectByPrimaryKey(Integer id) {
        return thirdClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ThirdClass record) {
        return thirdClassMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ThirdClass record) {
        return thirdClassMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ThirdClass> selectByCid(int cid) {
        return thirdClassMapper.selectByCid(cid);
    }
}
