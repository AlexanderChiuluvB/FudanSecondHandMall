package com.fduexchange.dao;

import com.fduexchange.pojo.AllSales;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AllSalesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AllSales record);

    int insertSelective(AllSales record);

    AllSales selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AllSales record);

    int updateByPrimaryKey(AllSales record);

    List<AllSales> selectTen(Map map);

    List<AllSales> selectOffShelf(Integer uid, Integer start);

    int getCountsOffShelf(Integer uid);

    int getCounts();

    int selectIdByImage(String image);

    List<AllSales> selectByName(String name);

    //通过分类选择
    @Select("select * from AllSales where sort=#{sort} and display =1 limit 12")
    List<AllSales> selectBySort(int sort);

    //选择用户的发布
    @Select("select * from AllSales where uid=#{uid} and display=1 order by id desc limit 12")
    List<AllSales> selectUserReleaseByUid(int uid);
}