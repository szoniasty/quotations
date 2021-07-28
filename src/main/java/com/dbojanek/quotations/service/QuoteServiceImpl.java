package com.dbojanek.quotations.service;

import com.dbojanek.quotations.model.Author;
import com.dbojanek.quotations.model.Quote;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final Map<Long, Quote> quotes;
    private Long quotesCounter = 3L;

    public QuoteServiceImpl() {
        quotes = new HashMap<>();

        quotes.put(1L, new Quote("Before you marry a person, you should first make them use a computer " +
                "with slow Internet to see who they really are.", new Author("Will", "Ferrell")));
        quotes.put(2L, new Quote("I haven’t spoken to my wife in years. I didn’t want to interrupt her.",
                new Author("Rodney", "Dangerfield")));
        quotes.put(3L, new Quote("You know you’ve reached middle age when you’re cautioned to slow down by " +
                "your doctor, instead of by the police.", new Author("Joan", "Rivers")));
    }

    @Override
    public Map<Long, Quote> getQuotes() {
        return quotes;
    }

    @Override
    public Quote getQuote(Long id) {
        return quotes.get(id);
    }

    @Override
    public Quote addQuote(Quote quote) {

        if (isQuoteEmpty(quote)) {
            throw new InvalidParameterException("invalid parameters");
        }

        return quotes.put(++quotesCounter, quote);
    }

    @Override
    public void removeQuote(Long id) {
        quotes.remove(id);
    }

    @Override
    public Quote updateQuote(Long id, Quote quote) {
        if (isQuoteEmpty(quote)) {
            throw new InvalidParameterException("Must provide at least one field to update.");
        }

        Quote toUpdate = quotes.get(id);
        if (toUpdate == null) {
            return null;
        }
        if (StringUtils.isNotEmpty(quote.getQuote())) {
            toUpdate.setQuote(quote.getQuote());
        }

        if (quote.getAuthor() != null) {
            if (StringUtils.isNotEmpty(quote.getAuthor().getName())) {
                toUpdate.getAuthor().setName(quote.getAuthor().getName());
            }
            if (StringUtils.isNotEmpty(quote.getAuthor().getSurname())) {
                toUpdate.getAuthor().setSurname(quote.getAuthor().getSurname());
            }
        }

        return toUpdate;
    }

    private boolean isQuoteEmpty(Quote quote) {
        return StringUtils.isEmpty(quote.getQuote()) &&
                (quote.getAuthor() == null ||
                        (StringUtils.isEmpty(quote.getAuthor().getName()) && StringUtils.isEmpty(quote.getAuthor().getSurname())));
    }
}
