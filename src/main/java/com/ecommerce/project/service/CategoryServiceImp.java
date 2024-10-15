package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .findFirst().orElse(null);

        if(category == null) {
            return "Category not found";
        }
        categories.remove(category);
        return "Category with CategoryId : "+ categoryId +" deleted successfully";
    }
}
