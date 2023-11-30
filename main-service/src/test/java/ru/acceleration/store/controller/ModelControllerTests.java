package ru.acceleration.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.acceleration.store.dto.model.ModelShortDto;
import ru.acceleration.store.dto.model.NewModelDto;
import org.springframework.test.web.servlet.MockMvc;
import ru.acceleration.store.service.model.ModelService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RequiredArgsConstructor
public class ModelControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ModelService modelService;

    NewModelDto productCreateDto = new NewModelDto("XY73GS33", "Apple iPhone 13 Pro Max 256GB", 100L);
    ModelShortDto productResponseDto = new ModelShortDto(1L,"XY73GS33", "Apple iPhone 13 Pro Max 256GB", 100L, new ArrayList<>());
//    NewModelDto productCreateDtoWithEmptyName = new NewModelDto("XY73GS33", "", 200L);
//    NewModelDto productCreateDtoWithEmptyVendorCode = new NewModelDto("", "Apple iPhone 13 Pro Max 256GB", 300L);

    @Test
    void postProductTest() throws Exception {
        Mockito.when(modelService.addModel(productCreateDto)).thenReturn(productResponseDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/models")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("XY73GS33"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Apple iPhone 13 Pro Max 256GB"));
    }

//    @Test
//    void postProductWithEmptyNameTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/models")
//                        .content(mapper.writeValueAsString(productCreateDtoWithEmptyName))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void postProductWithEmptyVendorCodeTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/models")
//                        .content(mapper.writeValueAsString(productCreateDtoWithEmptyVendorCode))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
}

