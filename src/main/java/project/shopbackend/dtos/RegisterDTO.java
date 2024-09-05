package project.shopbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @JsonProperty("full_name")
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @JsonProperty("user_name")
    @NotBlank(message = "User name cannot be blank")
    private String userName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "Retype password cannot be blank")
    private String retypePassword;

}
