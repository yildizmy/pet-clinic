package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.TypeRequestMapper;
import com.github.yildizmy.dto.mapper.TypeResponseMapper;
import com.github.yildizmy.dto.request.TypeRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.TypeResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Type;
import com.github.yildizmy.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit Test for TypeService methods
 */
@ExtendWith(MockitoExtension.class)
class TypeServiceTest {

    @InjectMocks
    private TypeService typeService;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeRequestMapper typeRequestMapper;

    @Mock
    private TypeResponseMapper typeResponseMapper;

    @Captor
    private ArgumentCaptor<Type> typeCaptor;

    /**
     * Method under test: {@link TypeService#findById(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void findById_should_returnTypeResponse_when_TypeIsFound(long id, String name, String description) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(id);
        typeResponse.setName(name);
        typeResponse.setDescription(description);

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));
        when(typeResponseMapper.toDto(type)).thenReturn(typeResponse);

        TypeResponse result = typeService.findById(id);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    /**
     * Method under test: {@link TypeService#findById(long)}
     */
    @Test
    void findById_should_throwNoSuchElementFoundException_when_TypeIsNotFound() {
        long id = 101L;
        when(typeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            typeService.findById(id);
        });

        verify(typeRepository).findById(id);
    }

    /**
     * Method under test: {@link TypeService#getById(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void getById_should_returnType_when_TypeIsFound(long id, String name, String description) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));

        Type result = typeService.getById(id);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    /**
     * Method under test: {@link TypeService#getById(long)}
     */
    @Test
    void getById_should_throwNoSuchElementFoundException_when_TypeIsNotFound() {
        long id = 101L;
        when(typeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            typeService.getById(id);
        });

        verify(typeRepository).findById(id);
    }

    /**
     * Method under test: {@link TypeService#findAll(Pageable)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void findAll_should_returnTypeResponsePage_when_TypeIsFound(long id, String name, String description) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        ArrayList<Type> typeList = new ArrayList<>();
        typeList.add(type);
        PageImpl<Type> pageImpl = new PageImpl<>(typeList);
        Pageable pageable = PageRequest.of(0, 10);

        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(id);
        typeResponse.setName(name);
        typeResponse.setDescription(description);

        when(typeRepository.findAll(pageable)).thenReturn(pageImpl);
        when(typeResponseMapper.toDto(type)).thenReturn(typeResponse);

        Page<TypeResponse> result = typeService.findAll(pageable);

        verify(typeResponseMapper).toDto(any());
        assertEquals(1, result.getContent().size());
        assertEquals(id, result.getContent().get(0).getId());
        assertEquals(name, result.getContent().get(0).getName());
        assertEquals(description, result.getContent().get(0).getDescription());
    }

    /**
     * Method under test: {@link TypeService#findAll(Pageable)}
     */
    @Test
    void findAll_should_throwNoSuchElementFoundException_when_TypeIsNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(typeRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        assertThrows(NoSuchElementFoundException.class, () -> {
            typeService.findAll(pageable);
        });

        verify(typeRepository).findAll(pageable);
    }

    /**
     * Method under test: {@link TypeService#create(TypeRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void create_should_returnCommandResponse_when_TypeIsCreated(long id, String name, String description) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        TypeRequest request = new TypeRequest();
        request.setId(id);
        request.setName(name);
        request.setDescription(description);

        when(typeRepository.existsByNameIgnoreCase(name)).thenReturn(false);
        when(typeRequestMapper.toEntity(request)).thenReturn(type);

        CommandResponse result = typeService.create(request);
        verify(typeRepository).save(typeCaptor.capture());
        Type capturedType = typeCaptor.getValue();

        assertEquals(capturedType.getId(), result.id());
        assertEquals(name, capturedType.getName());
        assertEquals(description, capturedType.getDescription());
    }

    /**
     * Method under test: {@link TypeService#create(TypeRequest)}
     */
    @Test
    void create_should_throwElementAlreadyExistsException_when_TypeAlreadyExists() {
        TypeRequest request = new TypeRequest();
        request.setName("Dog");

        when(typeRepository.existsByNameIgnoreCase("Dog")).thenReturn(true);

        assertThrows(ElementAlreadyExistsException.class, () -> {
            typeService.create(request);
        });

        verify(typeRepository, never()).save(any());
    }

    /**
     * Method under test: {@link TypeService#update(TypeRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void update_should_returnCommandResponse_when_TypeNameIsSame(long id, String name, String description, String updatedName, String updatedDescription) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        TypeRequest request = new TypeRequest();
        request.setId(id);
        request.setName(name);
        request.setDescription(updatedDescription);

        Type updatedType = new Type();
        updatedType.setId(id);
        updatedType.setName(updatedName);
        updatedType.setDescription(updatedDescription);

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));
        when(typeRequestMapper.toEntity(request)).thenReturn(updatedType);

        CommandResponse result = typeService.update(request);
        verify(typeRepository).save(typeCaptor.capture());
        Type capturedType = typeCaptor.getValue();

        assertEquals(capturedType.getId(), result.id());
        assertEquals(updatedName, capturedType.getName());
        assertEquals(updatedDescription, capturedType.getDescription());
    }

    /**
     * Method under test: {@link TypeService#update(TypeRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/types.csv")
    void update_should_returnCommandResponse_when_TypeNameIsDifferent(long id, String name, String description, String updatedName, String updatedDescription) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDescription(description);

        TypeRequest request = new TypeRequest();
        request.setId(id);
        request.setName(updatedName);
        request.setDescription(updatedDescription);

        Type updatedType = new Type();
        updatedType.setId(id);
        updatedType.setName(updatedName);
        updatedType.setDescription(updatedDescription);

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));
        when(typeRepository.existsByNameIgnoreCase(updatedName)).thenReturn(false);
        when(typeRequestMapper.toEntity(request)).thenReturn(updatedType);

        CommandResponse result = typeService.update(request);
        verify(typeRepository).save(typeCaptor.capture());
        Type capturedType = typeCaptor.getValue();

        assertEquals(capturedType.getId(), result.id());
        assertEquals(updatedName, capturedType.getName());
        assertEquals(updatedDescription, capturedType.getDescription());
    }

    /**
     * Method under test: {@link TypeService#update(TypeRequest)}
     */
    @Test
    void update_should_throwNoSuchElementFoundException_when_TypeIsNotFound() {
        TypeRequest request = new TypeRequest();
        request.setId(101L);
        request.setName("Dog");

        when(typeRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            typeService.update(request);
        });

        verify(typeRepository, never()).existsByNameIgnoreCase(any());
        verify(typeRepository, never()).save(any());
    }

    /**
     * Method under test: {@link TypeService#update(TypeRequest)}
     */
    @Test
    void update_should_throwElementAlreadyExistsException_when_TypeAlreadyExists() {
        Type type = new Type();
        type.setId(101L);
        type.setName("Cat");

        TypeRequest request = new TypeRequest();
        request.setId(101L);
        request.setName("Dog");

        when(typeRepository.findById(101L)).thenReturn(Optional.of(type));
        when(typeRepository.existsByNameIgnoreCase("Dog")).thenReturn(true);

        assertThrows(ElementAlreadyExistsException.class, () -> {
            typeService.update(request);
        });

        verify(typeRepository, never()).save(any());
    }

    /**
     * Method under test: {@link TypeService#deleteById(long)}
     */
    @Test
    void deleteById_should_deleteType_when_TypeIsFound() {
        Type type = new Type();
        type.setId(101L);
        type.setName("Cat");

        when(typeRepository.findById(101L)).thenReturn(Optional.of(type));

        typeService.deleteById(101L);
        verify(typeRepository).delete(typeCaptor.capture());
        Type capturedType = typeCaptor.getValue();

        assertEquals(101L, capturedType.getId());
        assertEquals("Cat", capturedType.getName());
    }

    /**
     * Method under test: {@link TypeService#deleteById(long)}
     */
    @Test
    void deleteById_should_throwNoSuchElementFoundException_when_TypeIsNotFound() {
        long id = 101L;
        when(typeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            typeService.deleteById(id);
        });

        verify(typeRepository).findById(id);
        verify(typeRepository, never()).delete(any());
    }
}
