package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.PetRequestMapper;
import com.github.yildizmy.dto.mapper.PetResponseMapper;
import com.github.yildizmy.dto.request.PetRequest;
import com.github.yildizmy.dto.request.TypeSetRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.PetResponse;
import com.github.yildizmy.dto.response.TypeResponse;
import com.github.yildizmy.dto.response.UserResponse;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Pet;
import com.github.yildizmy.model.Type;
import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.PetRepository;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit Test for PetService methods
 */
@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetRequestMapper petRequestMapper;

    @Mock
    private PetResponseMapper petResponseMapper;

    @Mock
    private TypeService typeService;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    /**
     * Method under test: {@link PetService#findById(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/pets.csv")
    void findById_should_returnPetResponse_when_PetIsFound(long id, String name, long typeId, String typeName, long userId,
                                                           String firstName, String lastName, String fullName, String username) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);

        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(typeId);
        typeResponse.setName(typeName);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setUsername(username);
        userResponse.setFullName(fullName);

        PetResponse petResponse = new PetResponse();
        petResponse.setId(id);
        petResponse.setName(name);
        petResponse.setUser(userResponse);
        petResponse.setType(typeResponse);

        when(petRepository.findById(id)).thenReturn(Optional.of(pet));
        when(petResponseMapper.toDto(pet)).thenReturn(petResponse);

        PetResponse result = petService.findById(id);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(typeId, result.getType().getId());
        assertEquals(typeName, result.getType().getName());
        assertEquals(userId, result.getUser().getId());
        assertEquals(firstName, result.getUser().getFirstName());
        assertEquals(lastName, result.getUser().getLastName());
        assertEquals(fullName, result.getUser().getFullName());
        assertEquals(username, result.getUser().getUsername());
    }

    /**
     * Method under test: {@link PetService#findById(long)}
     */
    @Test
    void findById_should_throwNoSuchElementFoundException_when_PetIsNotFound() {
        long id = 101L;
        when(petRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            petService.findById(id);
        });

        verify(petRepository).findById(id);
    }

    /**
     * Method under test: {@link PetService#findAll(Pageable)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/pets.csv")
    void findAll_should_returnPetResponsePage_when_PetIsFound(long id, String name, long typeId, String typeName, long userId,
                                                              String firstName, String lastName, String fullName, String username) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(pet);
        PageImpl<Pet> pageImpl = new PageImpl<>(petList);
        Pageable pageable = PageRequest.of(0, 10);

        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(typeId);
        typeResponse.setName(typeName);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setUsername(username);
        userResponse.setFullName(fullName);

        PetResponse petResponse = new PetResponse();
        petResponse.setId(id);
        petResponse.setName(name);
        petResponse.setUser(userResponse);
        petResponse.setType(typeResponse);

        when(petRepository.findAll(pageable)).thenReturn(pageImpl);
        when(petResponseMapper.toDto(pet)).thenReturn(petResponse);

        Page<PetResponse> result = petService.findAll(pageable);

        verify(petResponseMapper).toDto(any());
        assertEquals(1, result.getContent().size());
        assertEquals(id, result.getContent().get(0).getId());
        assertEquals(name, result.getContent().get(0).getName());
        assertEquals(typeId, result.getContent().get(0).getType().getId());
        assertEquals(typeName, result.getContent().get(0).getType().getName());
        assertEquals(userId, result.getContent().get(0).getUser().getId());
        assertEquals(firstName, result.getContent().get(0).getUser().getFirstName());
        assertEquals(lastName, result.getContent().get(0).getUser().getLastName());
        assertEquals(fullName, result.getContent().get(0).getUser().getFullName());
        assertEquals(username, result.getContent().get(0).getUser().getUsername());
    }

    /**
     * Method under test: {@link PetService#findAll(Pageable)}
     */
    @Test
    void findAll_should_throwNoSuchElementFoundException_when_PetIsNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(petRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        assertThrows(NoSuchElementFoundException.class, () -> {
            petService.findAll(pageable);
        });

        verify(petRepository).findAll(pageable);
    }

    /**
     * Method under test: {@link PetService#findAllByUserId(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/pets.csv")
    void findAllByUserId_should_returnPetResponseList_when_PetIsFound(long id, String name, long typeId, String typeName, long userId,
                                                              String firstName, String lastName, String fullName, String username) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(pet);

        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(typeId);
        typeResponse.setName(typeName);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setUsername(username);
        userResponse.setFullName(fullName);

        PetResponse petResponse = new PetResponse();
        petResponse.setId(id);
        petResponse.setName(name);
        petResponse.setType(typeResponse);
        petResponse.setUser(userResponse);

        when(petRepository.findAllByUserId(userId)).thenReturn(petList);
        when(petResponseMapper.toDto(pet)).thenReturn(petResponse);

        List<PetResponse> result = petService.findAllByUserId(userId);

        verify(petResponseMapper).toDto(pet);
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        assertEquals(name, result.get(0).getName());
        assertEquals(typeId, result.get(0).getType().getId());
        assertEquals(typeName, result.get(0).getType().getName());
        assertEquals(userId, result.get(0).getUser().getId());
        assertEquals(firstName, result.get(0).getUser().getFirstName());
        assertEquals(lastName, result.get(0).getUser().getLastName());
        assertEquals(fullName, result.get(0).getUser().getFullName());
        assertEquals(username, result.get(0).getUser().getUsername());
    }

    /**
     * Method under test: {@link PetService#findAllByUserId(long)}
     */
    @Test
    void findAllByUserId_should_throwNoSuchElementFoundException_when_PetIsNotFound() {
        long id = 101L;
        when(petRepository.findAllByUserId(id)).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementFoundException.class, () -> {
            petService.findAllByUserId(id);
        });

        verify(petRepository).findAllByUserId(id);
    }

    /**
     * Method under test: {@link PetService#findAllByType(TypeSetRequest)}
     */
    @Test
    void findAllByType_should_returnFilteredTypes_when_TypesNotEmpty() {
        Type type1 = new Type();
        type1.setId(101L);
        type1.setName("Dog");
        Type type2 = new Type();
        type2.setId(102L);
        type2.setName("Cat");
        Type type3 = new Type();
        type3.setId(103L);
        type3.setName("Bird");

        Pet pet1 = new Pet();
        pet1.setType(type1);
        Pet pet2 = new Pet();
        pet2.setType(type2);
        Pet pet3 = new Pet();
        pet3.setType(type3);

        Set<Long> types = new HashSet<>(Arrays.asList(101L, 102L));
        TypeSetRequest typeSetRequest = new TypeSetRequest();
        typeSetRequest.setIds(types);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(pet1);
        petList.add(pet2);
        petList.add(pet3);

        when(petRepository.findAll()).thenReturn(petList);

        Map<String, Long> result = petService.findAllByType(typeSetRequest);

        assertEquals(2, result.size());
        assertEquals(1, result.get("Dog"));
        assertEquals(1, result.get("Cat"));
    }

    /**
     * Method under test: {@link PetService#findAllByType(TypeSetRequest)}
     */
    @Test
    void findAllByType_should_returnAllTypes_when_TypesEmpty() {
        Type type1 = new Type();
        type1.setId(101L);
        type1.setName("Dog");
        Type type2 = new Type();
        type2.setId(102L);
        type2.setName("Cat");
        Type type3 = new Type();
        type3.setId(103L);
        type3.setName("Bird");

        Pet pet1 = new Pet();
        pet1.setType(type1);
        Pet pet2 = new Pet();
        pet2.setType(type2);
        Pet pet3 = new Pet();
        pet3.setType(type3);

        Set<Long> types = new HashSet<>();
        TypeSetRequest typeSetRequest = new TypeSetRequest();
        typeSetRequest.setIds(types);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(pet1);
        petList.add(pet2);
        petList.add(pet3);

        when(petRepository.findAll()).thenReturn(petList);

        Map<String, Long> result = petService.findAllByType(typeSetRequest);

        assertEquals(3, result.size());
        assertEquals(1, result.get("Dog"));
        assertEquals(1, result.get("Cat"));
        assertEquals(1, result.get("Bird"));
    }

    /**
     * Method under test: {@link PetService#create(PetRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/pets.csv")
    void create_should_returnCommandResponse_when_PetIsCreated(long id, String name, long typeId, String typeName, long userId,
                                                               String firstName, String lastName, String fullName, String username) {
        Type type = new Type();
        type.setId(typeId);
        type.setName(typeName);

        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);

        PetRequest request = new PetRequest();
        request.setId(id);
        request.setName(name);
        request.setTypeId(type.getId());
        request.setUserId(user.getId());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setType(type);
        pet.setUser(user);

        when(petRequestMapper.toEntity(request)).thenReturn(pet);
        when(typeService.getById(type.getId())).thenReturn(type);
        when(userService.getById(user.getId())).thenReturn(user);

        CommandResponse result = petService.create(request);
        verify(petRepository).save(petCaptor.capture());
        Pet capturedPet = petCaptor.getValue();

        assertEquals(capturedPet.getId(), result.id());
        assertEquals(name, capturedPet.getName());
        assertEquals(typeId, capturedPet.getType().getId());
        assertEquals(typeName, capturedPet.getType().getName());
        assertEquals(userId, capturedPet.getUser().getId());
        assertEquals(firstName, capturedPet.getUser().getFirstName());
        assertEquals(lastName, capturedPet.getUser().getLastName());
        assertEquals(username, capturedPet.getUser().getUsername());
    }

    /**
     * Method under test: {@link PetService#update(PetRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/pets.csv")
    void update_should_returnCommandResponse_when_PetIsFound(long id, String name, long typeId, String typeName, long userId,
                                                             String firstName, String lastName, String fullName, String username) {
        Type type = new Type();
        type.setId(typeId);
        type.setName(typeName);

        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);

        PetRequest request = new PetRequest();
        request.setId(id);
        request.setName(name);
        request.setTypeId(type.getId());
        request.setUserId(user.getId());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setType(type);
        pet.setUser(user);

        when(petRepository.existsById(request.getId())).thenReturn(true);
        when(petRequestMapper.toEntity(request)).thenReturn(pet);
        when(typeService.getById(type.getId())).thenReturn(type);
        when(userService.getById(user.getId())).thenReturn(user);

        CommandResponse result = petService.update(request);
        verify(petRepository).save(petCaptor.capture());
        Pet capturedPet = petCaptor.getValue();

        assertEquals(capturedPet.getId(), result.id());
        assertEquals(name, capturedPet.getName());
        assertEquals(typeId, capturedPet.getType().getId());
        assertEquals(typeName, capturedPet.getType().getName());
        assertEquals(userId, capturedPet.getUser().getId());
        assertEquals(firstName, capturedPet.getUser().getFirstName());
        assertEquals(lastName, capturedPet.getUser().getLastName());
        assertEquals(username, capturedPet.getUser().getUsername());
    }

    /**
     * Method under test: {@link PetService#update(PetRequest)}
     */
    @Test
    void update_should_throwNoSuchElementFoundException_when_PetIsNotExist() {
        PetRequest request = new PetRequest();
        request.setId(101L);
        request.setTypeId(201L);
        request.setUserId(301L);
        when(petRepository.existsById(101L)).thenReturn(false);

        assertThrows(NoSuchElementFoundException.class, () -> {
            petService.update(request);
        });

        verify(typeService, never()).getById(201L);
        verify(userService, never()).getById(301L);
        verify(petRepository, never()).getById(101L);
    }

    /**
     * Method under test: {@link PetService#deleteById(long)}
     */
    @Test
    void deleteById_should_deletePet_when_PetIsFound() {
        Pet pet = new Pet();
        pet.setId(101L);
        pet.setName("Daisy");
        pet.setType(new Type());
        pet.setUser(new User());

        when(petRepository.findById(101L)).thenReturn(Optional.of(pet));

        petService.deleteById(101L);
        verify(petRepository).delete(petCaptor.capture());
        Pet capturedPet = petCaptor.getValue();

        assertEquals(101L, capturedPet.getId());
        assertEquals("Daisy", capturedPet.getName());
    }

    /**
     * Method under test: {@link PetService#deleteById(long)}
     */
    @Test
    void deleteById_should_throwNoSuchElementFoundException_when_PetIsNotFound() {
        long id = 101L;
        when(petRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            petService.deleteById(id);
        });

        verify(petRepository).findById(id);
        verify(petRepository, never()).delete(any());
    }
}
