package com.blog.application.project.Service;

import com.blog.application.project.Payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id);

    public void deleteCategory(Integer id);

    public List<CategoryDTO> getAllCategory();

    public CategoryDTO getCategoryById(Integer id);

    public List<CategoryDTO> searchCategoryByTitle(String keyword);
}
