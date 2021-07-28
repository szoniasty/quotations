package com.dbojanek.quotations.service;

import com.dbojanek.quotations.model.dto.QuoteDTO;
import com.dbojanek.quotations.model.dto.QuoteMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class QuoteServiceImplTest {

    private QuoteService quoteService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public QuoteService quoteService() {
            return new QuoteServiceImpl(new QuoteMapper());
        }
    }

    @BeforeEach
    public void setUp() {
        quoteService = new QuoteServiceImpl(new QuoteMapper());
    }

    @Test
    public void shouldGetAllQuotes() {
        // given & when
        List<QuoteDTO> quoteDTOS = quoteService.getQuotes();

        // then
        assertEquals(3, quoteDTOS.size());
        assertThat(quoteDTOS)
                .extracting(QuoteDTO::getId)
                .containsExactlyInAnyOrder(1L, 2L, 3L);
    }

    @Test
    public void shouldGetSingleQuote() {
        // given & when
        QuoteDTO quoteDTO = quoteService.getQuote(1L);

        // then
        assertNotNull(quoteDTO);
        assertThat(quoteDTO.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldAddQuote() {
        // given
        assertThat(quoteService.getQuotes().size()).isEqualTo(3);

        QuoteDTO newQuote = new QuoteDTO();
        newQuote.setQuote("test new quote");
        newQuote.setAuthorName("authorName");
        newQuote.setAuthorSurname("authorSurname");

        // when
        QuoteDTO addedQuote = quoteService.addQuote(newQuote);

        // then
        assertThat(quoteService.getQuotes().size()).isEqualTo(4);
        assertThat(addedQuote.getId()).isEqualTo(4);
    }

    @Test
    public void shouldNotAddQuote_incompleteNewQuote() {
        // given
        assertThat(quoteService.getQuotes().size()).isEqualTo(3);

        QuoteDTO newQuote = new QuoteDTO();
        newQuote.setQuote("test new quote");
        newQuote.setAuthorName("authorName");

        // when & then
        assertThatThrownBy(() -> quoteService.addQuote(newQuote))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Must provide all fields to add quote");
    }

    @Test
    public void shouldRemoveQuote() {
        // given
        assertThat(quoteService.getQuotes().size()).isEqualTo(3);

        // when
        quoteService.removeQuote(2L);

        // then
        assertThat(quoteService.getQuotes().size()).isEqualTo(2);
        assertThatThrownBy(() -> quoteService.getQuote(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No quote with id: 2");
    }

    @Test
    public void shouldNotRemoveQuote_noQuoteToRemove() {
        // given
        Long notExistingQuoteId = 99L;

        // when & then
        assertThatThrownBy(() -> quoteService.removeQuote(notExistingQuoteId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No quote for given id");
    }

    @Test
    public void shouldUpdateQuote() {
        // given
        QuoteDTO toBeUpdated = new QuoteDTO();
        toBeUpdated.setQuote("test new quote");
        toBeUpdated.setAuthorName("authorName");
        toBeUpdated.setAuthorSurname("authorSurname");

        // when
        QuoteDTO updatedQuote = quoteService.updateQuote(3L, toBeUpdated);

        // then
        assertThat(quoteService.getQuote(3L)).isEqualTo(updatedQuote);
    }

    @Test
    public void shouldNotUpdateQuote_invalidNewQuote() {
        // given
        QuoteDTO toBeUpdated = new QuoteDTO();
        toBeUpdated.setQuote("test new quote");
        toBeUpdated.setAuthorName("authorName");

        // when & then
        assertThatThrownBy(() -> quoteService.updateQuote(3L, toBeUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Must provide all fields to update.");
    }

    @Test
    public void shouldNotUpdateQuote_noQuoteToUpdate() {
        // given
        Long notExistingQuoteId = 99L;
        QuoteDTO toBeUpdated = new QuoteDTO();
        toBeUpdated.setQuote("test new quote");
        toBeUpdated.setAuthorName("authorName");
        toBeUpdated.setAuthorSurname("authorSurname");

        // when & then
        assertThat(quoteService.updateQuote(notExistingQuoteId, toBeUpdated)).isNull();
    }
}
