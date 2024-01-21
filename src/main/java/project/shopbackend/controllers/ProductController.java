package project.shopbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.shopbackend.dtos.ProductDTO;
import project.shopbackend.responses.ProductResponse;
import project.shopbackend.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid ProductDTO productDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(productService.createProduct(productDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "limit", defaultValue = "10") int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProductById(@ModelAttribute @Valid ProductDTO productDTO,
                                               BindingResult result,
                                               @PathVariable("id") Long id){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(productService.updateProductById(id,productDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        try{
            productService.deteleProductById(id);
            return ResponseEntity.ok("Delete successfully id = " + id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
