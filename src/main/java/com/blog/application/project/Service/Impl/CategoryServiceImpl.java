package com.blog.application.project.Service.Impl;

import com.blog.application.project.Exception.ResourceNotFoundException;
import com.blog.application.project.Model.Category;
import com.blog.application.project.Payload.CategoryDTO;
import com.blog.application.project.Repository.CategoryRepository;
import com.blog.application.project.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryTitle());

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOList = categoryRepository.findAll().stream().map((category -> modelMapper.map(category, CategoryDTO.class))).collect(Collectors.toList());

        return categoryDTOList;
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> searchCategoryByTitle(String keyword) {
        List<Category> category = categoryRepository.searchCategoryByTitle("%" + keyword + "%");

        List<CategoryDTO> categoryDTOList = category.stream().map((category1 -> modelMapper.map(category1, CategoryDTO.class))).collect(Collectors.toList());

        return categoryDTOList;
    }
}
