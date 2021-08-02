package com.dbojanek.quotations.model.dto;

import com.dbojanek.quotations.model.Author;
import com.dbojanek.quotations.model.Quote;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class QuoteMapperTest {

    private QuoteMapper quoteMapper = new QuoteMapper();

    @Test
    public void shouldMapQuoteToDTO() {
        // given
        Quote quote = new Quote(1L, "quote", new Author("author first name", "author second name"));

        // when
        QuoteDTO quoteDTO = quoteMapper.mapToDto(quote);

        // then
        assertNotNull(quoteDTO);
        assertEquals(1L, quoteDTO.getId());
        assertEquals("quote", quoteDTO.getQuote());
        assertEquals("author first name", quoteDTO.getAuthorName());
        assertEquals("author second name", quoteDTO.getAuthorSurname());
    }
}
