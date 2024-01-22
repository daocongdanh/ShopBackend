package project.shopbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDTO {
    @JsonProperty("full_name")
    private String fullName;

    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("shipping_address")
    private String shippingAddress;


    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("user_id")
    private Long userId;
}
