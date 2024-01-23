package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.OrderDetailDTO;
import project.shopbackend.exceptions.DataNotFoundException;
import project.shopbackend.models.Order;
import project.shopbackend.models.OrderDetail;
import project.shopbackend.models.Product;
import project.shopbackend.repositories.OrderDetailRepository;
import project.shopbackend.repositories.OrderRepository;
import project.shopbackend.repositories.ProductRepository;
import project.shopbackend.responses.OrderDetailResponse;
import project.shopbackend.services.OrderDetailService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<OrderDetailResponse> getAllOrderDetailsByOrderId(Long oid) {
        return orderDetailRepository.findAllByOrderId(oid)
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return convert(orderDetailRepository.save(OrderDetail.builder()
                        .price(orderDetailDTO.getPrice())
                        .quantity(orderDetailDTO.getQuantity())
                        .order(order)
                        .product(product)
                        .build()));
    }

    @Override
    public OrderDetailResponse getOrderDetailById(Long id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if(orderDetail.isPresent()){
            return convert(orderDetail.get());
        }
        else throw new DataNotFoundException("OrderDetail not found");
    }

    @Override
    public OrderDetailResponse updateOrderDetailById(Long id, OrderDetailDTO orderDetailDTO) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        if(orderDetailRepository.findById(id).isPresent()){
            return convert(orderDetailRepository.save(OrderDetail.builder()
                            .id(id)
                            .price(orderDetailDTO.getPrice())
                            .quantity(orderDetailDTO.getQuantity())
                            .order(order)
                            .product(product)
                            .build()));
        }
        else throw new DataNotFoundException("OrderDetail not found");
    }

    @Override
    public void deleteOrderDetailById(Long id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if(orderDetail.isPresent()){
            orderDetailRepository.deleteById(id);
        }
        else throw new DataNotFoundException("OrderDetail not found");
    }

    private OrderDetailResponse convert(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .build();
    }
}
