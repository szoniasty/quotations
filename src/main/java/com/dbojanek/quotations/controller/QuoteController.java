package com.dbojanek.quotations.controller;

import com.dbojanek.quotations.model.Quote;
import com.dbojanek.quotations.service.QuoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.Map;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    private QuoteServiceImpl quoteService;

    @GetMapping("")
    public ResponseEntity<Map<Long, Quote>> getQuotes() {
        Map<Long, Quote> quotes = quoteService.getQuotes();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuote(@PathVariable String id) {
        try {
            Long quoteId = Long.parseLong(id);
            Quote quote = quoteService.getQuote(quoteId);
            return new ResponseEntity<>(quote, HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> addQuote(@RequestBody Quote quote) {
        try {
            quoteService.addQuote(quote);

            return new ResponseEntity<>("Quote added.", HttpStatus.CREATED);
        } catch (InvalidParameterException ipe) {
            return new ResponseEntity<>("Invalid parameters provided.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeQuote(@PathVariable String id) {
        try {
            Long quoteId = Long.parseLong(id);
            quoteService.removeQuote(quoteId);

            return new ResponseEntity<>("Quote removed.", HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Invalid id value.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> updateQuote(@PathVariable String id, @RequestBody Quote quote) {
        try {
            Long quoteId = Long.parseLong(id);
            Quote updateQuote = quoteService.updateQuote(quoteId, quote);

            return updateQuote != null ? new ResponseEntity<>("Quote updated", HttpStatus.OK) :
                    new ResponseEntity<>("Quote for given id was not found.", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Invalid id value.", HttpStatus.BAD_REQUEST);
        } catch (InvalidParameterException ipe) {
            return new ResponseEntity<>("Must provide at least one parameter for update", HttpStatus.BAD_REQUEST);
        }
    }
}
