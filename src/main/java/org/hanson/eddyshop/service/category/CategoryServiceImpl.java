package org.hanson.eddyshop.service.category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.dto.request.category.AddCategory;
import org.hanson.eddyshop.exception.customizedExceptions.CategoryRelatedException;
import org.hanson.eddyshop.model.Category;
import org.hanson.eddyshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryRelatedException(ErrorConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryRelatedException(ErrorConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public String addCategory(AddCategory request) {
        categoryRepository.findByName(request.name()).ifPresent(
                category-> {throw new CategoryRelatedException(ErrorConstant.CATEGORY_ALREADY_EXIST);});
        Category category = new Category(request.name());
        categoryRepository.save(category);
        return SuccessConstant.CREATED;
    }

    @Override
    @Transactional
    public String updateCategory(Long categoryId, AddCategory request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryRelatedException(ErrorConstant.CATEGORY_NOT_FOUND));
        category.setName(request.name());
        categoryRepository.save(category);
        return SuccessConstant.UPDATED;
    }

    @Override
    @Transactional
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryRelatedException(ErrorConstant.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        return SuccessConstant.DELETED;
    }
}
