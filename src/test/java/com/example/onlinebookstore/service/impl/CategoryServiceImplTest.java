package com.example.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CategoryMapper;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            save() works properly and returns dto
            """)
    void save_WithValidRequestDto_ShouldReturnResponseDto() {
        Category category = getDefaultCategory();
        CategoryResponseDto expected = getDefaultCategoryResponseDto();
        CategoryRequestDto requestDto = getDefaultCategoryRequestDto();
        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto actual = categoryService.save(requestDto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @DisplayName("""
            Verify the category name with valid ID was returned
            """)
    void findById_WithValidCategoryId_ShouldReturnValidCategory() {
        Category category = getDefaultCategory();
        String expected = category.getName();
        CategoryResponseDto categoryResponseDto = getDefaultCategoryResponseDto();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);

        String actual = categoryService.findById(category.getId()).getName();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            findById() should return Exception with invalid ID
            """)
    void findById_WithNotValidCategoryId_ShouldThrowException() {
        Long invalidCategoryId = 100L;
        when(categoryRepository.findById(invalidCategoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(invalidCategoryId)
        );

        String expected = "Category with ID: " + invalidCategoryId
                + ", unfortunately was not be found.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(invalidCategoryId);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("""
            Getting all categories from database with valid books
            """)
    void getAll_WithValidCategories_ShouldReturnList() {
        Category category = getDefaultCategory();
        List<Category> categoryList = List.of(category);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Category> page = new PageImpl<>(categoryList, pageable, categoryList.size());
        CategoryResponseDto categoryResponseDto = getDefaultCategoryResponseDto();
        List<CategoryResponseDto> expected = List.of(categoryResponseDto);
        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);

        List<CategoryResponseDto> actual = categoryService.getAll(pageable);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
    }

    @Test
    @DisplayName("""
            Updated category name and return response dto
            """)
    void updateById_WithValidId_ShouldReturnBookResponseDto() {
        Category category = getDefaultCategory();
        CategoryRequestDto requestDto = getDefaultCategoryRequestDto();
        requestDto.setName("Detective genre");
        CategoryResponseDto expected = getDefaultCategoryResponseDto();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto actual = categoryService.updateById(category.getId(), requestDto);

        EqualsBuilder.reflectionEquals(actual, expected);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @DisplayName("""
            updateById() should throw Exception with incorrect ID
            """)
    void updateById_WithNotValidId_ShouldThrowException() {
        Long invalidId = 100L;
        CategoryRequestDto categoryRequestDto = getDefaultCategoryRequestDto();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateById(invalidId, categoryRequestDto)
        );

        String expected = "Category with ID: " + invalidId + ", unfortunately was not be founded.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    private Category getDefaultCategory() {
        return new Category()
            .setId(1L)
            .setName("Detective")
            .setDescription("Genre, describing the process of "
                + "research mysterious event");
    }

    private CategoryRequestDto getDefaultCategoryRequestDto() {
        return new CategoryRequestDto()
                .setName("Detective")
                .setDescription("Genre, describing the process of "
                        + "research mysterious event");
    }

    private CategoryResponseDto getDefaultCategoryResponseDto() {
        return new CategoryResponseDto()
                .setId(1L)
                .setName("Detective")
                .setDescription("Genre, describing the process of "
                        + "research mysterious event");
    }

}
