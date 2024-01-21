package project.shopbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDTO {
    @NotBlank(message = "Product name cannot be blank") // Kiểu chuỗi
    private String name;

    @NotNull(message = "Price cannot be blank")
    @Min(value=0, message="Price must be greater than or equal to 0")
    @Max(value=10000000, message = "Price must be less than or equal to 10,000,000")
    private Float price;

    @NotNull(message = "Sale cannot be blank")
    @Min(value=0, message="Sale must be greater than or equal to 0")
    @Max(value=100, message = "Sale must be less than or equal to 100")
    private Float sale;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Quantity cannot be blank")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;

    @NotNull(message = "Category Id cannot be blank")
    private Long categoryId;

    private MultipartFile thumbnail;

    private List<MultipartFile> images;
}
