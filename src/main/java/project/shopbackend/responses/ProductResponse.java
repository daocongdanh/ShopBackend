package project.shopbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import project.shopbackend.models.Category;
import project.shopbackend.models.ProductImage;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private Long id;

    private String name;

    private Float price;

    private Float sale;

    private String description;

    @JsonProperty("view_count")
    private Integer viewCount;

    private Integer quantity;

    private String category;

    private String thumbnail;

    private List<String> images;
}
