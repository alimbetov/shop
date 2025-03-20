package kz.shop.book.services.good;


import kz.shop.book.dto.good.CategoryDto;
import kz.shop.book.entities.good.Category;
import kz.shop.book.entities.good.CategoryGroup;
import kz.shop.book.mappers.CategoryMapper;
import kz.shop.book.repository.good.CategoryRepository;
import kz.shop.book.repository.good.CategoryGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryGroupRepository groupRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryByCode(String code) {
        return categoryRepository.findById(code)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + code));
    }

    public CategoryDto createCategory(CategoryDto dto) {
        if (categoryRepository.existsById(dto.getCode())) {
            throw new RuntimeException("Категория с таким кодом уже существует: " + dto.getCode());
        }

        CategoryGroup group = groupRepository.findById(dto.getGroupCode())
                .orElseThrow(() -> new RuntimeException("Группа не найдена: " + dto.getGroupCode()));

        Category entity = categoryMapper.toEntity(dto, group);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    public CategoryDto updateCategory(String code, CategoryDto dto) {
        Category category = categoryRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + code));

        category.setNameRu(dto.getNameRu());
        category.setNameKz(dto.getNameKz());
        category.setNameEn(dto.getNameEn());
        category.setIsPublic(dto.getIsPublic());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(String code) {
        categoryRepository.deleteById(code);
    }

    public List<CategoryDto> searchCategories(String query) {
        return categoryRepository
                .findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        query, query, query, query)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getAllCategoriesByGroupCode(String code) {
        return categoryRepository
                .findAllByGroupCode(code)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
