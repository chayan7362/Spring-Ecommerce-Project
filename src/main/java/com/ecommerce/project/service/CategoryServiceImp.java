package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private long nextId = 1l;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String addCategory(Category category) {
        categories.add(category);
        category.setCategoryId(nextId++);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(long categoryId) {
        Category category = categories.stream()
                .filter(e -> e.getCategoryId()==categoryId)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        if(category == null) {
            return "Category not found";
        }
        categories.remove(category);
        return "Category with CategoryId : "+ categoryId +" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category , long categoryId) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(e -> e.getCategoryId()==categoryId)
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        } else {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }
    }

}
