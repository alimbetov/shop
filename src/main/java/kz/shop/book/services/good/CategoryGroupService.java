package kz.shop.book.services.good;


import kz.shop.book.dto.good.CategoryGroupDto;
import kz.shop.book.entities.good.CategoryGroup;
import kz.shop.book.mappers.CategoryGroupMapper;
import kz.shop.book.repository.good.CategoryGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryGroupService {

    private final CategoryGroupRepository groupRepository;
    private final CategoryGroupMapper groupMapper;

    // ✅ Получить все публичные группы
    public List<CategoryGroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Получить по коду
    public CategoryGroupDto getGroupByCode(String code) {
        CategoryGroup group = groupRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("CategoryGroup не найдена: " + code));
        return groupMapper.toDto(group);
    }

    // ✅ Создать
    public CategoryGroupDto createGroup(CategoryGroupDto dto) {
        if (groupRepository.existsById(dto.getCode())) {
            throw new RuntimeException("Группа с таким кодом уже существует: " + dto.getCode());
        }
        CategoryGroup entity = groupMapper.toEntity(dto);
      if (entity.getCategories()==null){
          entity.setCategories(new ArrayList<>());
      }
        return groupMapper.toDto(groupRepository.save(entity));
    }

    // ✅ Обновить
    public CategoryGroupDto updateGroup(String code, CategoryGroupDto dto) {
        CategoryGroup group = groupRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Группа не найдена: " + code));

        group.setNameRu(dto.getNameRu());
        group.setNameKz(dto.getNameKz());
        group.setNameEn(dto.getNameEn());
        group.setIsPublic(dto.getIsPublic());

        return groupMapper.toDto(groupRepository.save(group));
    }

    // ✅ Удалить
    public void deleteGroup(String code) {
        groupRepository.deleteById(code);
    }

    // ✅ Поиск
    public List<CategoryGroupDto> searchGroups(String query) {
        return groupRepository
                .findByCodeContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrNameKzContainingIgnoreCaseOrNameEnContainingIgnoreCase(
                        query, query, query, query)
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }
}
