package com.github.yildizmy.controller;

import com.github.yildizmy.IntegrationTest;
import com.github.yildizmy.dto.request.PetRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PetControllerTest extends IntegrationTest {

    /**
     * Method under test: {@link PetController#findById(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findById_should_returnStatusIsOk_when_PetIsFound() throws Exception {
        mvc.perform((get("/api/v1/pets/{id}", 1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", equalTo("Lassie")));
    }

    /**
     * Method under test: {@link PetController#findById(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findById_should_returnStatusIsNotFound_when_PetIsNotFound() throws Exception {
        mvc.perform((get("/api/v1/pets/{id}", 999)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Method under test: {@link PetController#findAll(Pageable)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findAll_should_returnStatusIsOk() throws Exception {
        mvc.perform((get("/api/v1/pets")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].name").value("Charlie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(12));
    }

    /**
     * Method under test: {@link PetController#findAllByUserId(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findAllByUserId_should_returnStatusIsOk_when_PetIsFound() throws Exception {
        mvc.perform((get("/api/v1/pets/users/{userId}", 1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].name").value("Tweety"));
    }

    /**
     * Method under test: {@link PetController#findAllByUserId(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findAllByUserId_should_returnStatusIsNotFound_when_PetIsNotFound() throws Exception {
        mvc.perform((get("/api/v1/pets/users/{id}", 999)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Method under test: {@link PetController#findAllByUserId(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findAllByType_should_returnStatusIsOk_when_PetIsFound() throws Exception {
        mvc.perform((post("/api/v1/pets/types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"ids\":[1,2]\n}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[\"Cat\"]").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[\"Dog\"]").value(3));
    }

    /**
     * Method under test: {@link PetController#findAllByUserId(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void findAllByType_should_returnEmptyResult_when_PetIsNotFound() throws Exception {
        mvc.perform((post("/api/v1/pets/types"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"ids\":[999]\n}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    /**
     * Method under test: {@link PetController#create(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void create_should_returnStatusIsCreated_when_PetIsCreated() throws Exception {
        mvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"name\": \"Max\",\n\"typeId\": 2,\n\"userId\": 1\n}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", greaterThan(0)));
    }

    /**
     * Method under test: {@link PetController#create(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void create_should_returnStatusIsUnprocessableEntity_when_PetNameIsTooShort() throws Exception {
        mvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"name\": \"X\",\n\"typeId\": 2,\n\"userId\": 1\n}")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Method under test: {@link PetController#create(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void create_should_returnStatusIsUnprocessableEntity_when_PetNameIsTooLong() throws Exception {
        mvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"name\": \"Xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\",\n\"typeId\": 2,\n\"userId\": 1\n}")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Method under test: {@link PetController#update(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void update_should_returnStatusIsOk_when_PetIsUpdated() throws Exception {
        mvc.perform(put("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"id\": 1,\n\"name\": \"Flip\",\n\"typeId\": 3,\n\"userId\": 1\n}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1));
    }

    /**
     * Method under test: {@link PetController#update(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void update_should_returnStatusIsUnprocessableEntity_when_PetNameIsTooShort() throws Exception {
        mvc.perform(put("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"id\": 1,\n\"name\": \"X\",\n\"typeId\": 3,\n\"userId\": 1\n}")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Method under test: {@link PetController#update(PetRequest)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void update_should_returnStatusIsUnprocessableEntity_when_PetNameIsTooLong() throws Exception {
        mvc.perform(put("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n\"id\": 1,\n\"name\": \"Xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\",\n\"typeId\": 3,\n\"userId\": 1\n}")
                )
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Method under test: {@link PetController#deleteById(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteById_should_returnStatusIsNoContent_when_PetIsDeleted() throws Exception {
        mvc.perform(delete("/api/v1/pets/{id}", 1))
                .andExpect(status().isNoContent());
    }

    /**
     * Method under test: {@link PetController#deleteById(long)}
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteById_should_throwNoSuchElementFoundException_when_PetIsNotFound() throws Exception {
        mvc.perform(delete("/api/v1/pets/{id}", 999))
                .andExpect(status().isNotFound());
    }
}
