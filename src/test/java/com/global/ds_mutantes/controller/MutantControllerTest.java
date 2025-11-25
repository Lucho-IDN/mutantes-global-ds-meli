package com.global.ds_mutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.ds_mutantes.dto.DnaRequest;
import com.global.ds_mutantes.dto.StatsResponse;
import com.global.ds_mutantes.service.MutantService;
import com.global.ds_mutantes.service.StatsService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    // 1
    @Test
    @DisplayName("POST /mutant → 200 OK si es mutante")
    void testMutant200() throws Exception {
        String[] dna = {"AAAA", "CCCC", "TTTT", "GGGG"};
        when(mutantService.analyzeDna(dna)).thenReturn(true);

        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isOk());
    }

    // 2
    @Test
    @DisplayName("POST /mutant → 403 si es humano")
    void testHuman403() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        when(mutantService.analyzeDna(dna)).thenReturn(false);

        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isForbidden());
    }

    // 3
    @Test
    @DisplayName("POST /mutant → 400 si ADN es null")
    void testNullDna() throws Exception {
        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": null}"))
                .andExpect(status().isBadRequest());
    }

    // 4
    @Test
    @DisplayName("POST /mutant → 400 si el JSON es inválido")
    void testMalformedJson() throws Exception {
        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{bad json}"))
                .andExpect(status().isBadRequest());
    }

    // 5
    @Test
    @DisplayName("GET /stats retorna estadísticas")
    void testStats() throws Exception {
        StatsResponse stats = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(stats);

        mvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(40))
                .andExpect(jsonPath("$.countHumanDna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    // 6
    @Test
    @DisplayName("POST /mutant sin body → 400")
    void testEmptyBody() throws Exception {
        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // 7
    @Test
    @DisplayName("POST /mutant acepta application/json")
    void testAcceptJson() throws Exception {
        String[] dna = {"AAAA", "CCCC", "TTTT", "GGGG"};
        when(mutantService.analyzeDna(dna)).thenReturn(true);

        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isOk());
    }

    // 8
    @Test
    @DisplayName("POST /mutant → ADN vacío genera 400")
    void testEmptyDnaArray() throws Exception {
        mvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": []}"))
                .andExpect(status().isBadRequest());
    }
}
