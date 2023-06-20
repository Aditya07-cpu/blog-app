package com.blog.application.project.Repository;

import com.blog.application.project.Model.Category;
import com.blog.application.project.Payload.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category as c where c.categoryTitle LIKE :key")
    List<Category> searchCategoryByTitle(@Param("key") String keyword);
}
