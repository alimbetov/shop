package kz.shop.book.controllers;

import jakarta.validation.Valid;
import kz.shop.book.dto.good.CategoryDto;
import kz.shop.book.dto.good.CategoryGroupDto;
import kz.shop.book.dto.good.SubCategoryDto;
import kz.shop.book.services.good.CategoryGroupService;
import kz.shop.book.services.good.CategoryService;
import kz.shop.book.services.good.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/good")
@CrossOrigin(origins = "*") // ✅ Разрешаем CORS
@RequiredArgsConstructor
@Validated
public class GoodController {

    private final CategoryGroupService groupService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    // ========================== CATEGORY GROUP ==========================

    @GetMapping("/category-group")
    public List<CategoryGroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/category-group/search")
    public List<CategoryGroupDto> searchGroups(@RequestParam String query) {
        return groupService.searchGroups(query);
    }

    @PostMapping("/category-group")
    public CategoryGroupDto createGroup(@RequestBody @Valid CategoryGroupDto dto) {
        return groupService.createGroup(dto);
    }

    @PutMapping("/category-group/{code}")
    public CategoryGroupDto updateGroup(@PathVariable String code, @RequestBody @Valid CategoryGroupDto dto) {
        return groupService.updateGroup(code, dto);
    }

    @DeleteMapping("/category-group/{code}")
    public void deleteGroup(@PathVariable String code) {
        groupService.deleteGroup(code);
    }

    // ========================== CATEGORY ==========================

    @GetMapping("/category")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/search")
    public List<CategoryDto> searchCategories(@RequestParam String query) {
        return categoryService.searchCategories(query);
    }
    @GetMapping("/category/search-by-parent")
    public List<CategoryDto> searchCategoriesByGroupCode(@RequestParam String query) {
        return categoryService.getAllCategoriesByGroupCode(query);
    }

    @PostMapping("/category")
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/category/{code}")
    public CategoryDto updateCategory(@PathVariable String code, @RequestBody @Valid CategoryDto dto) {
        return categoryService.updateCategory(code, dto);
    }

    @DeleteMapping("/category/{code}")
    public void deleteCategory(@PathVariable String code) {
        categoryService.deleteCategory(code);
    }

    // ========================== SUB CATEGORY ==========================

    @GetMapping("/sub-category")
    public List<SubCategoryDto> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/sub-category/search")
    public List<SubCategoryDto> searchSubCategories(@RequestParam String query) {
        return subCategoryService.searchSubCategories(query);
    }
    @GetMapping("/sub-category/search-by-parent")
    public List<SubCategoryDto> searchSubCategoriesByCategoryCode(@RequestParam String query) {
        return subCategoryService.searchSubCategoriesByCategoryCode(query);
    }

    @PostMapping("/sub-category")
    public SubCategoryDto createSubCategory(@RequestBody @Valid SubCategoryDto dto) {
        return subCategoryService.createSubCategory(dto);
    }

    @PutMapping("/sub-category/{code}")
    public SubCategoryDto updateSubCategory(@PathVariable String code, @RequestBody @Valid SubCategoryDto dto) {
        return subCategoryService.updateSubCategory(code, dto);
    }

    @DeleteMapping("/sub-category/{code}")
    public void deleteSubCategory(@PathVariable String code) {
        subCategoryService.deleteSubCategory(code);
    }
}
