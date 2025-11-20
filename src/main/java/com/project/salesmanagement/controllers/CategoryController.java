package com.project.salesmanagement.controllers;

import com.project.salesmanagement.components.LocalizationUtils;
import com.project.salesmanagement.dtos.CategoryDTO;
import com.project.salesmanagement.models.Category;
import com.project.salesmanagement.responses.ResponseObject;
import com.project.salesmanagement.services.category.CategoryService;
import com.project.salesmanagement.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
//@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    // Lấy danh sách danh mục
    // GET http://localhost:8088/api/v1/categories
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategories(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(
          ResponseObject.builder()
            .message("Get list of categories successfully")
            .status(HttpStatus.OK)
            .data(categories)
            .build()
        );
    }

    // Thêm mới 1 danh mục
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createCategory(
      @Valid @RequestBody CategoryDTO categoryDTO,
      BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
              .stream()
              .map(FieldError::getDefaultMessage)
              .toList();
            return ResponseEntity.ok().body(ResponseObject.builder()
              .message(errorMessages.toString())
              .status(HttpStatus.BAD_REQUEST)
              .data(null)
              .build());

        }
        Category category = categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok().body(ResponseObject.builder()
          .message("Create category successfully")
          .status(HttpStatus.OK)
          .data(category)
          .build());
    }

    // Cập nhật danh mục
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateCategory(
      @PathVariable Long id,
      @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(ResponseObject
          .builder()
          .data(categoryService.getCategoryById(id))
          .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY))
          .build());
    }

    //  Xóa mềm 1 danh mục
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
          ResponseObject.builder()
            .status(HttpStatus.OK)
            .message("Delete category successfully")
            .build());
    }

    // Lấy thông tin chi tiết 1 danh mục
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(
      @PathVariable("id") Long categoryId
    ) {
        Category existingCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(ResponseObject.builder()
          .data(existingCategory)
          .message("Get category information successfully")
          .status(HttpStatus.OK)
          .build());
    }
}

