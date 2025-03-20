package kz.shop.book.services.good;

import kz.shop.book.dto.good.SubCategoryDto;
import kz.shop.book.entities.good.Category;
import kz.shop.book.entities.good.SubCategory;
import kz.shop.book.mappers.SubCategoryMapper;
import kz.shop.book.repository.good.CategoryRepository;
import kz.shop.book.repository.good.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public List<SubCategoryDto> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public SubCategoryDto getSubCategoryByCode(String code) {
        return subCategoryRepository.findById(code)
                .map(subCategoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Подкатегория не найдена: " + code));
    }

    public SubCategoryDto createSubCategory(SubCategoryDto dto) {
        if (subCategoryRepository.existsById(dto.getCode())) {
            throw new RuntimeException("Подкатегория с таким кодом уже существует: " + dto.getCode());
        }

        Category category = categoryRepository.findById(dto.getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + dto.getCategoryCode()));

        SubCategory entity = subCategoryMapper.toEntity(dto, category);
        return subCategoryMapper.toDto(subCategoryRepository.save(entity));
    }

    public SubCategoryDto updateSubCategory(String code, SubCategoryDto dto) {
        SubCategory subCategory = subCategoryRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Подкатегория не найдена: " + code));

        subCategory.setNameRu(dto.getNameRu());
        subCategory.setNameKz(dto.getNameKz());
        subCategory.setNameEn(dto.getNameEn());
        subCategory.setIsPublic(dto.getIsPublic());

        return subCategoryMapper.toDto(subCategoryRepository.save(subCategory));
    }

    public void deleteSubCategory(String code) {
        subCategoryRepository.deleteById(code);
    }

    public List<SubCategoryDto> searchSubCategories(String query) {
        return subCategoryRepository
                .findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        query, query, query, query)
                .stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SubCategoryDto> searchSubCategoriesByCategoryCode(String code) {
        return subCategoryRepository
                .findAllByCategoryCode(code)
                .stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
