package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.ProductDTO;
import project.shopbackend.exceptions.DataNotFoundException;
import project.shopbackend.exceptions.DuplicateException;
import project.shopbackend.models.Category;
import project.shopbackend.models.Product;
import project.shopbackend.models.ProductImage;
import project.shopbackend.repositories.CategoryRepository;
import project.shopbackend.repositories.ProductImageRepository;
import project.shopbackend.repositories.ProductRepository;
import project.shopbackend.responses.ProductResponse;
import project.shopbackend.services.ProductService;
import project.shopbackend.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return convert(product.get(), product.get().getImages());
        }
        else throw new DataNotFoundException("Product not found");
    }


    @Override
    public List<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.getContent().stream()
                .map(product -> convert(product, product.getImages()))
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductDTO productDTO) throws IOException{
        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if(category.isEmpty())
            throw new DataNotFoundException("Category not found");
        // Chỉ cho upload 4 ảnh
        if(productDTO.getImages().size() != 4){
            throw new FileUploadException("Number of images must be 4");
        }
        if(!productRepository.existsByName(productDTO.getName())){
            String thumbnail = fileUtil.uploadSingleFile(productDTO.getThumbnail());
            List<String> images = fileUtil.uploadMultiFile(productDTO.getImages());
            Product product = productRepository.save(
                    Product.builder()
                            .name(productDTO.getName())
                            .price(productDTO.getPrice())
                            .sale(productDTO.getSale())
                            .description(productDTO.getDescription())
                            .viewCount(0)
                            .quantity(productDTO.getQuantity())
                            .thumbnail(thumbnail)
                            .category(category.get())
                            .build()
            );
            List<ProductImage> productImages = new ArrayList<>();
            for (String image : images){
                ProductImage productImage = ProductImage.builder()
                        .url(image)
                        .product(product)
                        .build();
                productImages.add(productImage);
                productImageRepository.save(productImage);
            }
            return convert(product,productImages);
        }
        else throw new DuplicateException("Duplicate product name");
    }

    @Override
    public ProductResponse updateProductById(Long id, ProductDTO productDTO) throws IOException{
        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if(category.isEmpty())
            throw new DataNotFoundException("Category not found");
        // Chỉ cho upload 4 ảnh
        if(productDTO.getImages().size() != 4){
            throw new FileUploadException("Number of images must be 4");
        }
        if(!productRepository.existsByName(productDTO.getName())){
            Optional<Product> existingProduct= productRepository.findById(id);
            if(existingProduct.isPresent()){
                Product product = existingProduct.get();
                // Xóa ảnh trong project
                fileUtil.deleteFile(product.getThumbnail());
                for(ProductImage image : product.getImages()){
                    fileUtil.deleteFile(image.getUrl());
                }
                // Thêm ảnh mới vào csdl
                String thumbnail = fileUtil.uploadSingleFile(productDTO.getThumbnail());
                List<String> images = fileUtil.uploadMultiFile(productDTO.getImages());

                List<ProductImage> productImages = productImageRepository.findByProductId(id);
                for(int i=0;i<4;i++){
                    productImages.get(i).setUrl(images.get(i));
                }
                Product productUpdate = productRepository.save(
                        Product.builder()
                                .id(id)
                                .name(productDTO.getName())
                                .price(productDTO.getPrice())
                                .sale(productDTO.getSale())
                                .description(productDTO.getDescription())
                                .viewCount(0)
                                .quantity(productDTO.getQuantity())
                                .thumbnail(thumbnail)
                                .category(category.get())
                                .images(productImages)
                                .build()
                );
                return convert(productUpdate,productImages);
            }
            else throw new DataNotFoundException("Product not found");
        }
        else throw new DuplicateException("Duplicate product name");
    }

    @Override
    public void deteleProductById(Long id) {
        Optional<Product> existingProduct= productRepository.findById(id);

        if(existingProduct.isPresent()){
            Product product = existingProduct.get();
            // Xóa ảnh trong project
            fileUtil.deleteFile(product.getThumbnail());
            for(ProductImage image : product.getImages()){
                fileUtil.deleteFile(image.getUrl());
            }
            productRepository.deleteById(id);
        }
        else throw new DataNotFoundException("Product not found");
    }

    private ProductResponse convert(Product product, List<ProductImage> images){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .sale(product.getSale())
                .description(product.getDescription())
                .viewCount(product.getViewCount())
                .quantity(product.getQuantity())
                .thumbnail(product.getThumbnail())
                .category(product.getCategory().getName())
                .images(images.stream()
                        .map(image ->image.getUrl())
                        .toList()
                )
                .build();
    }
}
