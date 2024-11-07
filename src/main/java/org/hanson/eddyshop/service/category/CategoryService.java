package org.hanson.eddyshop.service.category;

import org.hanson.eddyshop.dto.request.category.AddCategory;
import org.hanson.eddyshop.model.Category;
import java.util.List;

public interface CategoryService  {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    String addCategory(AddCategory category);
    String updateCategory(Long CategoryId, AddCategory category);
    String deleteCategory(Long id);
}
