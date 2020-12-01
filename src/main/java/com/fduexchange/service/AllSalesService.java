package com.fduexchange.service;

import com.fduexchange.pojo.AllSales;

import java.util.List;
import java.util.Map;


public interface AllSalesService {
    int deleteByPrimaryKey(Integer id);

    int insert(AllSales record);

    int insertSelective(AllSales record);

    AllSales selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AllSales record);

    int updateByPrimaryKey(AllSales record);

    List<AllSales> selectTen(Map map);

    List<AllSales> selectOffShelf(int uid, int start);

    int getCountsOffShelf(int uid);

    int getCounts();

    int selectIdByImage(String image);

    List<AllSales> selectByName(String name);

    List<AllSales> selectBySort(int sort);

    List<AllSales> selectUserReleaseByUid(int uid);
}
