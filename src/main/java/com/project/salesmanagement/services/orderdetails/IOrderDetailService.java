package com.project.salesmanagement.services.orderdetails;

import com.project.salesmanagement.dtos.OrderDetailDTO;
import com.project.salesmanagement.exceptions.DataNotFoundException;
import com.project.salesmanagement.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);


}
