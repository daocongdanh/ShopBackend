package project.shopbackend.services;

import project.shopbackend.dtos.CategoryDTO;
import project.shopbackend.models.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategoryById(Long id, CategoryDTO categoryDTO);

    void deteleCategoryById(Long id);

}
