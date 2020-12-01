package com.fduexchange.service.Impl;

import com.fduexchange.dao.ShopMessageMapper;
import com.fduexchange.pojo.ShopMessage;
import com.fduexchange.service.ShopMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ShopMessageServiceImpl implements ShopMessageService {
    @Resource
    private ShopMessageMapper shopMessageMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ShopMessage record) {
        return shopMessageMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopMessage record) {
        return shopMessageMapper.insertSelective(record);
    }

    @Override
    public ShopMessage selectByPrimaryKey(Integer id) {
        return shopMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopMessage record) {
        return shopMessageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopMessage record) {
        return shopMessageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int getCounts(int sid) {
        return shopMessageMapper.getCounts(sid);
    }

    @Override
    public List<ShopMessage> findById(int sid, int start) {
        return shopMessageMapper.findById(sid,start);
    }

    @Override
    public List<ShopMessage> selectById(int id) {
        return shopMessageMapper.selectBySid(id);
    }
}
