package com.dbojanek.quotations.model.dto;

import com.dbojanek.quotations.model.Quote;
import org.springframework.stereotype.Service;

@Service
public class QuoteMapper {

    public QuoteDTO mapToDto(Quote quote) {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setId(quote.getId());
        quoteDTO.setQuote(quote.getQuote());
        quoteDTO.setAuthorName(quote.getAuthor().getName());
        quoteDTO.setAuthorSurname(quote.getAuthor().getSurname());

        return quoteDTO;
    }
}
