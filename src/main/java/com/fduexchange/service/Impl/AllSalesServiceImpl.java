package com.fduexchange.service.Impl;

import com.fduexchange.dao.AllSalesMapper;
import com.fduexchange.pojo.AllSales;
import com.fduexchange.service.AllSalesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class AllSalesServiceImpl implements AllSalesService {

    @Resource
    private AllSalesMapper allSalesMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(AllSales record) {
        return allSalesMapper.insert(record);
    }

    @Override
    public int insertSelective(AllSales record) {
        return allSalesMapper.insertSelective(record);
    }

    @Override
    public AllSales selectByPrimaryKey(Integer id) {
        return allSalesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AllSales record) {
        return allSalesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AllSales record) {
        return allSalesMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<AllSales> selectTen(Map map) {
        return allSalesMapper.selectTen(map);
    }

    @Override
    public List<AllSales> selectOffShelf(int uid, int start) {
        return allSalesMapper.selectOffShelf(uid,start);
    }

    @Override
    public int getCountsOffShelf(int uid) {
        return allSalesMapper.getCountsOffShelf(uid);
    }

    @Override
    public int getCounts() {
        return allSalesMapper.getCounts();
    }

    @Override
    public int selectIdByImage(String image) {
        return allSalesMapper.selectIdByImage(image);
    }

    @Override
    public List<AllSales> selectByName(String name){
        return allSalesMapper.selectByName(name);
    }

    @Override
    public List<AllSales> selectBySort(int sort) {
        return allSalesMapper.selectBySort(sort);
    }

    @Override
    public List<AllSales> selectUserReleaseByUid(int uid) {
        return allSalesMapper.selectUserReleaseByUid(uid);
    }
}
