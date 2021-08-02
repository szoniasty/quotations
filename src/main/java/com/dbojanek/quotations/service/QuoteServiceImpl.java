package com.dbojanek.quotations.service;

import com.dbojanek.quotations.model.Author;
import com.dbojanek.quotations.model.Quote;
import com.dbojanek.quotations.model.dto.QuoteDTO;
import com.dbojanek.quotations.model.dto.QuoteMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final Map<Long, Quote> quotes;
    private AtomicLong quotesCounter;
    private final QuoteMapper quoteMapper;

    @Autowired
    public QuoteServiceImpl(QuoteMapper quoteMapper) {
        this.quoteMapper = quoteMapper;
        this.quotes = new ConcurrentHashMap<>();
        this.quotesCounter = new AtomicLong(3L);

        quotes.put(1L, new Quote(1L, "Before you marry a person, you should first make them use a computer " +
                "with slow Internet to see who they really are.", new Author("Will", "Ferrell")));
        quotes.put(2L, new Quote(2L, "I haven’t spoken to my wife in years. I didn’t want to interrupt her.",
                new Author("Rodney", "Dangerfield")));
        quotes.put(3L, new Quote(3L, "You know you’ve reached middle age when you’re cautioned to slow down by " +
                "your doctor, instead of by the police.", new Author("Joan", "Rivers")));
    }

    @Override
    public List<QuoteDTO> getQuotes() {
        List<QuoteDTO> quoteDTOS = new LinkedList<>();
        for (Map.Entry<Long, Quote> entry : quotes.entrySet()) {
            quoteDTOS.add(quoteMapper.mapToDto(entry.getValue()));
        }

        quoteDTOS.sort(Comparator.comparing(QuoteDTO::getId));
        return quoteDTOS;
    }

    @Override
    public QuoteDTO getQuote(Long id) {
        try {
            return quoteMapper.mapToDto(quotes.get(id));
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("No quote with id: " + id);
        }
    }

    @Override
    public QuoteDTO addQuote(QuoteDTO quoteDTO) {

        if (isQuoteDtoIncomplete(quoteDTO)) {
            throw new IllegalArgumentException("Must provide all fields to add quote");
        }

        long nextId = quotesCounter.incrementAndGet();

        Quote newQuote = new Quote(nextId, quoteDTO.getQuote(), new Author(quoteDTO.getAuthorName(), quoteDTO.getAuthorSurname()));
        quotes.put(nextId, newQuote);

        return quoteMapper.mapToDto(newQuote);
    }

    @Override
    public void removeQuote(Long id) {
        if (quotes.get(id) == null) {
            throw new IllegalArgumentException("No quote for given id");
        }
        quotes.remove(id);
    }

    @Override
    public QuoteDTO updateQuote(Long id, QuoteDTO quoteDTO) {
        if (isQuoteDtoIncomplete(quoteDTO)) {
            throw new IllegalArgumentException("Must provide all fields to update.");
        }

        Quote toUpdate = quotes.get(id);
        if (toUpdate == null) {
            return null;
        }

        toUpdate.setQuote(quoteDTO.getQuote());
        toUpdate.getAuthor().setName(quoteDTO.getAuthorName());
        toUpdate.getAuthor().setSurname(quoteDTO.getAuthorSurname());

        return quoteMapper.mapToDto(quotes.get(id));
    }

    private boolean isQuoteDtoIncomplete(QuoteDTO quote) {
        return StringUtils.isEmpty(quote.getQuote()) ||
                StringUtils.isEmpty(quote.getAuthorName()) ||
                StringUtils.isEmpty(quote.getAuthorSurname());
    }
}
