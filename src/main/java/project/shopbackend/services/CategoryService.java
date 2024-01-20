package project.shopbackend.services;

import project.shopbackend.dtos.CategoryDTO;
import project.shopbackend.models.Category;
import project.shopbackend.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {


    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse createCategory(CategoryDTO categoryDTO);

    CategoryResponse updateCategoryById(Long id, CategoryDTO categoryDTO);

    void deteleCategoryById(Long id);

}
