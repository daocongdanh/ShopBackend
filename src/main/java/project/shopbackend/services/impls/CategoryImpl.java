package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.CategoryDTO;
import project.shopbackend.exceptions.DataNotFoundException;
import project.shopbackend.models.Category;
import project.shopbackend.repositories.CategoryRepository;
import project.shopbackend.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(404,"Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .status(true)
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryById(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            return categoryRepository.save(Category.builder()
                            .name(categoryDTO.getName())
                            .status(true)
                            .build());
        }
        else throw new DataNotFoundException(404,"Category not found");
    }

    @Override
    public void deteleCategoryById(Long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()){
            categoryRepository.deleteById(id);
        }
        else throw new DataNotFoundException(404,"Category not found");
    }
}
