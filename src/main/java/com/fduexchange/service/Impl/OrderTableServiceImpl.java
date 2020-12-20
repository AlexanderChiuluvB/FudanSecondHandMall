package com.fduexchange.service.Impl;

import com.fduexchange.dao.OrderTableMapper;
import com.fduexchange.pojo.AllSales;
import com.fduexchange.pojo.OrderTable;
import com.fduexchange.service.OrderTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class OrderTableServiceImpl implements OrderTableService {
    @Resource
    private OrderTableMapper OrderTableMapper;

    @Override
    public int insertSelective(OrderTable record) {
        return OrderTableMapper.insertSelective(record);
    }

    @Override
    public int insert(OrderTable record) {
        return OrderTableMapper.insert(record);
    }

    @Override
    public List<OrderTable> selectBySellerId(int seller_id){
        return OrderTableMapper.selectBySellerId(seller_id);
    }

    @Override
    public List<OrderTable> selectByPurchaserId(int purchaser_id) {
        return OrderTableMapper.selectByPurchaserId(purchaser_id);
    }

    @Override
    public int updateState(OrderTable record) {
        return OrderTableMapper.updateState(record);
    }

}
