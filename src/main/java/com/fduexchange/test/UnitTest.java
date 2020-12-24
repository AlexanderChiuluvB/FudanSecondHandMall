package com.fduexchange.test;

import com.fduexchange.pojo.AllSales;
import com.fduexchange.pojo.OrderTable;
import com.fduexchange.service.AllSalesService;
import com.fduexchange.service.OrderTableService;
import com.fduexchange.service.UserReleaseService;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class UnitTest {
    @Resource
    private OrderTableService orderTableService;
    @Test
    public void testOrder() {
        List<OrderTable> expectedList = new ArrayList<>();

        OrderTable orderTable = new OrderTable();
        orderTable.setOrder_id(10);
        orderTable.setPurchaser_id(1);
        orderTable.setSeller_id(2);
        orderTableService.insert(orderTable);

        expectedList.add(orderTable);

        List<OrderTable> purchaserOrderTableList = orderTableService.selectByPurchaserId(1);
        System.out.println(purchaserOrderTableList);
        assert purchaserOrderTableList == expectedList;

        List<OrderTable> sellerOrderTableList = orderTableService.selectBySellerId(2);
        System.out.println(sellerOrderTableList);
        assert sellerOrderTableList == expectedList;
    }
}
