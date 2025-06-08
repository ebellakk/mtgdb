package com.kallebe.mtgdb.scryfall.facade;

import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import com.kallebe.mtgdb.scryfall.filters.ScryfallSearchFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ScryfallFacadeIntegrationTest {

    @Autowired
    private ScryfallFacade scryfallFacade;

    @Test
    public void testRandomCard() {
        var random = scryfallFacade.random();
        assertNotNull(random);
        assertNotNull(random.id());
    }

    @Test
    public void givenQueryThatMatchesNoCardThenAutocompleteReturnsEmptyListOfCards() {
        var query = "nonexistingcard";
        var autocomplete = scryfallFacade.autocomplete(query);
        assertNotNull(autocomplete);
        var autoCompleteCards = autocomplete.data();
        assertNotNull(autoCompleteCards);
        assertTrue(autoCompleteCards.isEmpty());
    }

    @Test
    public void givenQueryThatMatchesCardsThenAutocompleteReturnsListOfCards() {
        var query = "Well";
        var autocomplete = scryfallFacade.autocomplete(query);
        assertNotNull(autocomplete);
        var autoCompleteCards = autocomplete.data();
        assertNotNull(autoCompleteCards);
        assertFalse(autoCompleteCards.isEmpty());
        // Check that the query is a substring from all the cards
        autoCompleteCards.forEach(card -> assertTrue(card.toLowerCase().contains(query.toLowerCase())));
    }

    @Test
    public void givenAnIdThatDoesNotMatchACardThenGetCardByIdReturns404() {
        var id = "123321";
        var exception = assertThrows(ScryfallIntegrationException.class, () -> scryfallFacade.getCardById(id));
        String expectedMessage = "Card not found";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void givenValidIdThenGetCardByIdReturnsCardData() {
        var id = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        var wellwisher = scryfallFacade.getCardById(id);
        assertNotNull(wellwisher);
        assertNotNull(wellwisher.id());
        assertEquals("Wellwisher", wellwisher.name());
    }

    @Test
    public void givenQueryThenSearchReturnsCorrectResults() {
        var page = 1;
        var query = "wellwisher";
        var filterBuilder = new ScryfallSearchFilter.ScryfallSearchFilterBuilder();
        filterBuilder.query(query);
        var filter = filterBuilder.build();
        var result = scryfallFacade.search(filter, page);
        assertNotNull(result);
        var cards = result.data();
        var length = result.total_cards();
        assertEquals(1, length);
        var card = cards.getFirst();
        var expectedId = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        assertEquals(expectedId, card.id());
        assertEquals("Wellwisher", card.name());
    }
}
