package com.dbojanek.quotations.controller;

import com.dbojanek.quotations.model.dto.QuoteDTO;
import com.dbojanek.quotations.service.QuoteService;
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

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public ResponseEntity<List<QuoteDTO>> getQuotes() {
        List<QuoteDTO> quotes = quoteService.getQuotes();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDTO> getQuote(@PathVariable Long id) {
        try {
            QuoteDTO quote = quoteService.getQuote(id);
            return new ResponseEntity<>(quote, HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<QuoteDTO> addQuote(@RequestBody QuoteDTO quote) {
        try {
            QuoteDTO quoteDTO = quoteService.addQuote(quote);

            return new ResponseEntity<>(quoteDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeQuote(@PathVariable Long id) {
        try {
            quoteService.removeQuote(id);

            return new ResponseEntity<>("Quote removed.", HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("Invalid id value.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> updateQuote(@PathVariable Long id, @RequestBody QuoteDTO quoteDTO) {
        try {
            QuoteDTO updatedQuote = quoteService.updateQuote(id, quoteDTO);

            return updatedQuote != null ? new ResponseEntity<>("Quote updated", HttpStatus.OK) :
                    new ResponseEntity<>("Quote for given id was not found.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("Must provide all parameters.", HttpStatus.BAD_REQUEST);
        }
    }
}
