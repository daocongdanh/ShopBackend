package project.shopbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import project.shopbackend.enums.OrderStatus;
import project.shopbackend.models.User;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDateTime shippingDate;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("user_id")
    private Long userId;
}
