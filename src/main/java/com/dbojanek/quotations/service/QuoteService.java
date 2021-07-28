package com.dbojanek.quotations.service;

import com.dbojanek.quotations.model.Quote;

import java.util.Map;

public interface QuoteService {
    Map<Long, Quote> getQuotes();

    Quote getQuote(Long id);

    Quote addQuote(Quote quote);

    void removeQuote(Long id);

    Quote updateQuote(Long id, Quote quote);
}
