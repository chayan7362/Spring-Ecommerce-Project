package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import io.micrometer.common.KeyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

//    private List<Category> categories = new ArrayList<>();
    private long nextId = 1l;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String addCategory(Category category) {
        category.setCategoryId(nextId++);
        categoryRepository.save(category);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(long categoryId) {
        List<Category> categories = categoryRepository.findAll();

        Category category = categories.stream()
                .filter(e -> e.getCategoryId()==categoryId)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        if(category == null) {
            return "Category not found";
        }
        categoryRepository.delete(category);
        return "Category with CategoryId : "+ categoryId +" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category , long categoryId) {

        List<Category> categories = categoryRepository.findAll();
        Optional<Category> optionalCategory = categories.stream()
                .filter(e -> e.getCategoryId()==categoryId)
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save((existingCategory));
            return savedCategory;
        } else {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }
    }

}
