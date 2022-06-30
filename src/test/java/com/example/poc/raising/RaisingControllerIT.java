package com.example.poc.raising;

import com.example.poc.stockEtablissement.StockEtablissement;
import com.example.poc.stockEtablissement.StockEtablissementEnum;
import com.example.poc.stockEtablissement.StockEtablissementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RaisingControllerIT {

    private static final int ROW_LIMIT = 2000000;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockEtablissementRepository stockEtablissementRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldReturnDefaultMessageWhenUseStreamService() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/raising-request")
                        .content(asJsonString(
                                new RaisingRequest(
                                        Arrays.asList(StockEtablissementEnum.SIREN.header, StockEtablissementEnum.SIRET.header),
                                        "STREAM",
                                        ROW_LIMIT,
                                        "https://files.data.gouv.fr/insee-sirene/StockEtablissement_utf8.zip")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("All data raised and stored in DB."));

        long rowCountActual = stockEtablissementRepository.count();
        assertEquals(ROW_LIMIT, rowCountActual);

        StockEtablissement firstRow = stockEtablissementRepository.findById(1L).get();
        assertNotNull(firstRow.getSiret());
        assertNotNull(firstRow.getSiren());
        assertNull(firstRow.getActivitePrincipaleEtablissement());
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/raising-request")
                        .content(asJsonString(
                                new RaisingRequest(
                                        Arrays.asList(StockEtablissementEnum.SIREN.header, StockEtablissementEnum.SIRET.header),
                                        "NORMAL",
                                        ROW_LIMIT,
                                        "https://files.data.gouv.fr/insee-sirene/StockEtablissement_utf8.zip")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("All data raised and stored in DB."));

    }


}
