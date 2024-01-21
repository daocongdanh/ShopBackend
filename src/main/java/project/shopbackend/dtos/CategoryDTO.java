package project.shopbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CategoryDTO {
    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 255, message = "Category name must be less than or equal to 255 characters")
    private String name;
}
