package com.dbojanek.quotations.service;

import com.dbojanek.quotations.model.dto.QuoteDTO;

import java.util.List;

public interface QuoteService {
    List<QuoteDTO> getQuotes();

    QuoteDTO getQuote(Long id);

    QuoteDTO addQuote(QuoteDTO quoteDTO);

    void removeQuote(Long id);

    QuoteDTO updateQuote(Long id, QuoteDTO quoteDTO);
}
