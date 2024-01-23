package project.shopbackend.services;

import project.shopbackend.dtos.OrderDetailDTO;
import project.shopbackend.responses.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getAllOrderDetailsByOrderId(Long oid);
    OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailResponse getOrderDetailById(Long id);
    OrderDetailResponse updateOrderDetailById(Long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetailById(Long id);

}
