package project.shopbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
    @JsonProperty("full_name")
    @NotBlank(message = "Full Name cannot be blank")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone Number cannot be blank")
    private String phoneNumber;

    @JsonProperty("shipping_address")
    @NotBlank(message = "Shipping Address cannot be blank")
    private String shippingAddress;


    @JsonProperty("total_money")
    @NotNull(message = "Total money cannot be blank")
    private Float totalMoney;

    @JsonProperty("user_id")
    @NotNull(message = "User Id cannot be blank")
    private Long userId;
}
