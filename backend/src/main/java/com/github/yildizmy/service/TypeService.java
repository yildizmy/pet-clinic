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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.yildizmy.common.Constants.*;

/**
 * Service used for Type related operations
 */
@Slf4j(topic = "TypeService")
@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeRequestMapper typeRequestMapper;
    private final TypeResponseMapper typeResponseMapper;

    /**
     * Fetches a single type by the given id
     *
     * @param id
     * @return TypeResponse
     */
    public TypeResponse findById(long id) {
        return typeRepository.findById(id)
                .map(typeResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TYPE));
    }

    /**
     * Fetches a single type (entity) by the given id
     *
     * @param id
     * @return Type
     */
    public Type getById(long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TYPE));
    }

    /**
     * Fetches all types based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of TypeResponse
     */
    @Transactional(readOnly = true)
    public Page<TypeResponse> findAll(Pageable pageable) {
        final Page<TypeResponse> types = typeRepository.findAll(pageable)
                .map(typeResponseMapper::toDto);

        if (types.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return types;
    }

    /**
     * Creates a new type using the given request parameters
     *
     * @param request
     * @return id of the created type
     */
    public CommandResponse create(TypeRequest request) {
        if (typeRepository.existsByNameIgnoreCase(request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_TYPE);

        final Type type = typeRequestMapper.toEntity(request);
        typeRepository.save(type);
        log.info(CREATED_TYPE);
        return CommandResponse.builder().id(type.getId()).build();
    }

    /**
     * Updates type using the given request parameters
     *
     * @param request
     * @return id of the updated type
     */
    public CommandResponse update(TypeRequest request) {
        final Type type = typeRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TYPE));

        // if the name value of the request is different, check if a record with this name already exists
        if (!request.getName().equalsIgnoreCase(type.getName()) && typeRepository.existsByNameIgnoreCase(request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_TYPE);

        typeRepository.save(typeRequestMapper.toEntity(request));
        log.info(UPDATED_TYPE);
        return CommandResponse.builder().id(type.getId()).build();
    }

    /**
     * Deletes type by the given id
     *
     * @param id
     */
    public void deleteById(long id) {
        final Type type = typeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TYPE));
        typeRepository.delete(type);
        log.info(DELETED_TYPE);
    }
}
