package org.hanson.eddyshop.controller;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.request.category.AddCategory;
import org.hanson.eddyshop.model.Category;
import org.hanson.eddyshop.service.category.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    @ResponseStatus(OK)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    @ResponseStatus(CREATED)
    public String saveCategory(@RequestBody AddCategory category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("category/{categoryId}")
    @ResponseStatus(OK)
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("category/{categoryName}")
    @ResponseStatus(OK)
    public Category getCategoryByName(@PathVariable String categoryName) {
        return categoryService.getCategoryByName(categoryName);
    }

    @DeleteMapping("category/{categoryId}")
    @ResponseStatus(NO_CONTENT)
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @PatchMapping("category/{categoryId}/update")
    @ResponseStatus(OK)
    public String updateCategory(@PathVariable Long categoryId, @RequestBody AddCategory category) {
        return categoryService.updateCategory(categoryId,category);
    }
}
