package com.ihr.controller;

import com.ihr.dto.CandidateDto;
import com.ihr.service.CandidateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.ihr.factory.CandidateFactory.getCandidateDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CandidateServiceImpl candidateService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSave() throws Exception {
        CandidateDto candidateDto = getCandidateDto();
        when(candidateService.save(any())).thenReturn(candidateDto);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/candidate")
                        .content(objectMapper.writeValueAsString(candidateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
        CandidateDto result = objectMapper.readValue(contentAsString, CandidateDto.class);

        assertThat(result).usingRecursiveComparison().isEqualTo(candidateDto);
    }

    @Test
    void testSaveFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/candidate")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        CandidateDto candidateDto = getCandidateDto();
        candidateDto.setName(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/candidate")
                        .content(objectMapper.writeValueAsString(candidateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void findByName() throws Exception {
        when(candidateService.findByName(any())).thenReturn(List.of(getCandidateDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/candidate")
                        .param("name", "Ivan"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[0].address").value("New York"));
    }

    @Test
    void updateAddress() throws Exception {
        when(candidateService.updateAddress(any(), any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/candidate/1")
                        .param("newAddress", "London"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$").value("true"));
    }
}