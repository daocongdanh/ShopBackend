package project.shopbackend.services;

import project.shopbackend.dtos.OrderDTO;
import project.shopbackend.responses.OrderResponse;

import java.util.List;

public interface OrderService {

    List<OrderResponse> getAllOrdersByUserId(Long uid);
    OrderResponse getOrderById(Long id);
    OrderResponse createOrder(OrderDTO orderDTO);
    OrderResponse updateOrderById(Long id, OrderDTO orderDTO);
    void deleteOrderById(Long id);
}
