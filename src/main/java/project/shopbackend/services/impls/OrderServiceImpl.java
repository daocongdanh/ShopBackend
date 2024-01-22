package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.OrderDTO;
import project.shopbackend.enums.OrderStatus;
import project.shopbackend.exceptions.DataNotFoundException;
import project.shopbackend.models.Order;
import project.shopbackend.models.User;
import project.shopbackend.repositories.OrderRepository;
import project.shopbackend.repositories.UserRepository;
import project.shopbackend.responses.OrderResponse;
import project.shopbackend.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Override
    public List<OrderResponse> getAllOrdersByUserId(Long uid) {
        return orderRepository.findAllByUserId(uid)
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            return convert(order.get());
        }
        else throw new DataNotFoundException("Order not found");
    }

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        if(user.isPresent()){
            return convert(orderRepository.save(Order.builder()
                            .orderDate(LocalDateTime.now())
                            .fullName(orderDTO.getFullName())
                            .email(orderDTO.getEmail())
                            .phoneNumber(orderDTO.getPhoneNumber())
                            .shippingAddress(orderDTO.getShippingAddress())
//                            .shippingDate()
                            .totalMoney(orderDTO.getTotalMoney())
                            .orderStatus(OrderStatus.PENDING)
                            .user(user.get())
                    .build()));
        }
        else throw new DataNotFoundException("User not found");
    }

    @Override
    public OrderResponse updateOrderById(Long id, OrderDTO orderDTO) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            Optional<User> user = userRepository.findById(orderDTO.getUserId());
            if(user.isPresent()){
                return convert(orderRepository.save(Order.builder()
                        .id(id)
                        .orderDate(LocalDateTime.now())
                        .fullName(orderDTO.getFullName())
                        .email(orderDTO.getEmail())
                        .phoneNumber(orderDTO.getPhoneNumber())
                        .shippingAddress(orderDTO.getShippingAddress())
//                            .shippingDate()
                        .totalMoney(orderDTO.getTotalMoney())
                        .orderStatus(OrderStatus.PENDING)
                        .user(user.get())
                        .build()));
            }
            else throw new DataNotFoundException("User not found");
        }
        else throw new DataNotFoundException("Order not found");
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            orderRepository.deleteById(id);
        }
        else throw new DataNotFoundException("Order not found");
    }
    private OrderResponse convert(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .totalMoney(order.getTotalMoney())
                .orderStatus(order.getOrderStatus().name())
                .userId(order.getUser().getId())
                .build();
    }
}
