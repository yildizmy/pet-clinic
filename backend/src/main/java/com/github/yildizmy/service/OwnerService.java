package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.OwnerRequestMapper;
import com.github.yildizmy.dto.mapper.OwnerResponseMapper;
import com.github.yildizmy.dto.request.OwnerRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.OwnerResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Owner;
import com.github.yildizmy.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.yildizmy.common.Constants.*;

/**
 * Service used for Owner related operations
 */
@Slf4j(topic = "OwnerService")
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerRequestMapper ownerRequestMapper;
    private final OwnerResponseMapper ownerResponseMapper;

    /**
     * Fetches a single owner by the given id
     *
     * @param id
     * @return
     */
    public OwnerResponse findById(long id) {
        return ownerRepository.findById(id)
                .map(ownerResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_OWNER));
    }

    /**
     * Fetches all owners based on the given paging and sorting +parameters
     *
     * @param pageable
     * @return List of OwnerResponse
     */
    @Transactional(readOnly = true)
    public Page<OwnerResponse> findAll(Pageable pageable) {
        final Page<OwnerResponse> owners = ownerRepository.findAll(pageable)
                .map(ownerResponseMapper::toDto);

        if (owners.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return owners;
    }

    /**
     * Creates a new owner using the given request parameters
     *
     * @param request
     * @return id of the created owner
     */
    public CommandResponse create(OwnerRequest request) {
        if (ownerRepository.existsByEmailIgnoreCase(request.getEmail()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_OWNER);

        final Owner owner = ownerRequestMapper.toEntity(request);
        ownerRepository.save(owner);
        log.info(CREATED_OWNER);
        return CommandResponse.builder().id(owner.getId()).build();
    }

    /**
     * Updates owner using the given request parameters
     *
     * @param request
     * @return id of the updated owner
     */
    public CommandResponse update(OwnerRequest request) {
        final Owner owner = ownerRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_OWNER));

        // if the email value of the request is different, check if a record with this email already exists
        if (!request.getEmail().equalsIgnoreCase(owner.getEmail()) && ownerRepository.existsByEmailIgnoreCase(request.getEmail()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_OWNER);

        ownerRepository.save(ownerRequestMapper.toEntity(request));
        log.info(UPDATED_OWNER);
        return CommandResponse.builder().id(owner.getId()).build();
    }

    /**
     * Deletes owner by the given id
     *
     * @param id
     */
    public void deleteById(long id) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_OWNER));
        ownerRepository.delete(owner);
        log.info(DELETED_OWNER);
    }
}
