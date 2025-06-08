package com.kallebe.mtgdb.card.controller;

import com.kallebe.mtgdb.card.model.dto.AutoCompleteDto;
import com.kallebe.mtgdb.card.model.dto.CardDto;
import com.kallebe.mtgdb.card.model.dto.SearchCardResultDto;
import com.kallebe.mtgdb.error.ErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private <T> T get(String url, Class<T> classType) {
        return this.restTemplate.getForObject("http://localhost:" + port + "/" + url, classType);
    }

    @Test
    public void testRandomCard() {
        var url = "cards/random";
        var randomCard = this.get(url, CardDto.class);
        assertNotNull(randomCard);
        assertNotNull(randomCard.id());
    }

    @Test
    public void givenQueryThatMatchesCardsThenAutocompleteReturnsListOfCards() {
        var query = "Well";
        var url = String.format("cards/autocomplete?query=%s", query);
        var autocomplete = this.get(url, AutoCompleteDto.class);
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
        var url = String.format("cards/%s", id);
        var error = this.get(url, ErrorDto.class);
        String expectedMessage = "Card not found";
        assertEquals(expectedMessage, error.message());
        assertEquals(HttpStatus.NOT_FOUND.value(), error.status());
    }

    @Test
    public void givenValidIdThenGetCardByIdReturnsCardData() {
        var id = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        var url = String.format("cards/%s", id);
        var wellwisher = this.get(url, CardDto.class);
        assertNotNull(wellwisher);
        assertNotNull(wellwisher.id());
        assertEquals("Wellwisher", wellwisher.name());
    }

    @Test
    public void givenNoQueryOrFullQueryThenSearchShouldThrowBadRequest() {
        var url = "cards/search";
        var error = this.get(url, ErrorDto.class);
        String expectedMessage = "At least one of 'query' or 'fullQuery' must be provided";
        assertEquals(expectedMessage, error.message());
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.status());
    }

    @Test
    public void givenQueryThenSearchReturnsCorrectResults() {
        var query = "wellwisher";
        var url = String.format("cards/search?query=%s", query);
        var result = this.get(url, SearchCardResultDto.class);
        assertNotNull(result);
        var cards = result.data();
        var length = result.totalCards();
        assertEquals(1, length);
        var card = cards.getFirst();
        var expectedId = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        assertEquals(expectedId, card.id());
        assertEquals("Wellwisher", card.name());
    }

    @Test
    public void givenFullQueryThenSearchPerformsTheGivenQuery() {
        var fullQuery = "krenko+mob type:goblin type:legend mana:2RR";
        var url = String.format("cards/search?fullQuery=%s", fullQuery);
        var result = this.get(url, SearchCardResultDto.class);
        assertNotNull(result);
        var cards = result.data();
        var length = result.totalCards();
        assertEquals(1, length);
        var card = cards.getFirst();
        var expectedId = "824b2d73-2151-4e5e-9f05-8f63e2bdcaa9";
        assertEquals(expectedId, card.id());
        assertEquals("Krenko, Mob Boss", card.name());
    }
}
