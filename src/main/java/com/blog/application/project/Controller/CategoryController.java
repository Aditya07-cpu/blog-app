package com.blog.application.project.Controller;

import com.blog.application.project.Payload.CategoryDTO;
import com.blog.application.project.Payload.ResponseObject;
import com.blog.application.project.Service.Impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseObject responseObject = new ResponseObject();

        CategoryDTO categoryDto = categoryService.createCategory(categoryDTO);

        responseObject.setMessage("Category Created Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(categoryDto);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Integer categoryId) {
        ResponseObject responseObject = new ResponseObject();

        CategoryDTO categoryDto = categoryService.updateCategory(categoryDTO, categoryId);

        responseObject.setMessage("Category With Id: " + categoryId + " Updated Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(categoryDto);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Integer categoryId) {
        ResponseObject responseObject = new ResponseObject();

        categoryService.deleteCategory(categoryId);

        responseObject.setMessage("Category With Id: " + categoryId + " Deleted Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(null);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Integer categoryId) {
        ResponseObject responseObject = new ResponseObject();

        CategoryDTO categoryDto = categoryService.getCategoryById(categoryId);

        responseObject.setMessage("Category Id With: " + categoryId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(categoryDto);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllCategory() {
        ResponseObject responseObject = new ResponseObject();

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategory();

        responseObject.setMessage("Fetched All Category");
        responseObject.setSuccess(true);
        responseObject.setData(categoryDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ResponseObject> searchCategoryByTitle(@PathVariable String keyword) {
        ResponseObject responseObject = new ResponseObject();

        List<CategoryDTO> categoryDTOList = categoryService.searchCategoryByTitle(keyword);

        responseObject.setMessage("Category With Keyword: " + keyword + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(categoryDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
