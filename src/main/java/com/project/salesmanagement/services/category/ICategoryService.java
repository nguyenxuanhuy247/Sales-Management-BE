package com.project.salesmanagement.services.category;

import com.project.salesmanagement.dtos.CategoryDTO;
import com.project.salesmanagement.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    Category deleteCategory(long id) throws Exception;

}
