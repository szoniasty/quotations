package com.dbojanek.quotations.controller;

import com.dbojanek.quotations.model.dto.QuoteDTO;
import com.dbojanek.quotations.service.QuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuoteController.class)
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuoteService quoteService;

    @Test
    public void whenGetQuotesIsServiceUp_shouldReturn200() throws Exception {
        mockMvc.perform(get("/quote"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSingleQuoteIsValidInput_shouldReturn200() throws Exception {
        mockMvc.perform(get("/quote/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSingleQuoteIsInvalidInput_shouldReturn404() throws Exception {
        when(quoteService.getQuote(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/quote/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenAddQuoteIsValidInput_shouldReturn201() throws Exception {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setQuote("quote");
        quoteDTO.setAuthorName("name");
        quoteDTO.setAuthorSurname("surname");

        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenAddQuoteIsInvalidInput_shouldReturn400() throws Exception {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setQuote("quote");
        quoteDTO.setAuthorName("name");

        when(quoteService.addQuote(any())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRemoveQuoteIsValidInput_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/quote/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveQuoteIsInvalidInput_shouldReturn404() throws Exception {
        doThrow(IllegalArgumentException.class).when(quoteService).removeQuote(anyLong());

        mockMvc.perform(delete("/quote/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateQuoteIsValidInput_shouldReturn200() throws Exception {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setQuote("quote");
        quoteDTO.setAuthorName("name");
        quoteDTO.setAuthorSurname("surname");

        when(quoteService.updateQuote(any(), any())).thenReturn(new QuoteDTO());

        mockMvc.perform(put("/quote/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateQuoteIsInvalidInput_shouldReturn400() throws Exception {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setQuote("quote");
        quoteDTO.setAuthorName("name");
        quoteDTO.setAuthorSurname("surname");

        when(quoteService.updateQuote(any(), any())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(put("/quote/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateQuoteIsInvalidInput_shouldReturn404() throws Exception {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setQuote("quote");
        quoteDTO.setAuthorName("name");
        quoteDTO.setAuthorSurname("surname");

        when(quoteService.updateQuote(any(), any())).thenReturn(null);

        mockMvc.perform(put("/quote/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isNotFound());
    }
}
