package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.CategoryDTO;
import project.shopbackend.exceptions.DataNotFoundException;
import project.shopbackend.exceptions.DuplicateException;
import project.shopbackend.models.Category;
import project.shopbackend.repositories.CategoryRepository;
import project.shopbackend.responses.CategoryResponse;
import project.shopbackend.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private CategoryResponse convert(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
    @Override
    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return convert(category.get());
        }
        else throw new DataNotFoundException("Category not found");
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> convert(category))
                .toList();
    }

    @Override
    public CategoryResponse createCategory(CategoryDTO categoryDTO) {
        if(!categoryRepository.existsByName(categoryDTO.getName())){
            Category category = Category.builder()
                    .name(categoryDTO.getName())
                    .build();
            return convert(categoryRepository.save(category));
        }
        else throw new DuplicateException("Duplicate category name");
    }

    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryDTO categoryDTO) {
        if(!categoryRepository.existsByName(categoryDTO.getName())){
            Optional<Category> existingCategory = categoryRepository.findById(id);
            if(existingCategory.isPresent()){
                return convert(categoryRepository.save(Category.builder()
                        .id(id)
                        .name(categoryDTO.getName())
                        .build()));
            }
            else throw new DataNotFoundException("Category not found");
        }
        else throw new DuplicateException("Duplicate category name");
    }

    @Override
    public void deteleCategoryById(Long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()){
            categoryRepository.deleteById(id);
        }
        else throw new DataNotFoundException("Category not found");
    }
}
