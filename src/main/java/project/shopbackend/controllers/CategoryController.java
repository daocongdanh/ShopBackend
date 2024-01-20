package project.shopbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.shopbackend.dtos.CategoryDTO;
import project.shopbackend.responses.CategoryResponse;
import project.shopbackend.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@RequestBody @Valid CategoryDTO categoryDTO,
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
            return ResponseEntity.ok(categoryService.updateCategoryById(id,categoryDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Long id){
        try {
            categoryService.deteleCategoryById(id);
            return ResponseEntity.ok("Delete successfully id = " + id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
