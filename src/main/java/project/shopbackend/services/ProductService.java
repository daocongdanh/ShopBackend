package project.shopbackend.services;

import org.springframework.data.domain.Pageable;
import project.shopbackend.dtos.ProductDTO;
import project.shopbackend.responses.ProductResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse createProduct(ProductDTO productDTO) throws IOException;

    ProductResponse updateProductById(Long id, ProductDTO productDTO) throws IOException;

    void deteleProductById(Long id);
}
