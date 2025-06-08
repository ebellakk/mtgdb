package com.kallebe.mtgdb.scryfall.service;

import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import com.kallebe.mtgdb.scryfall.facade.ScryfallFacade;
import com.kallebe.mtgdb.scryfall.filters.ScryfallSearchFilter;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallAutoCompleteDto;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallCardDto;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallSearchCardResultDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScryfallServiceTest {
    @InjectMocks
    private ScryfallService scryfallService;

    @Mock
    private ScryfallFacade scryfallFacade;

    @Test
    public void testRandomCard() {
        var id = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        var card = new ScryfallCardDto.ScryfallCardDtoBuilder().id(id).build();

        when(scryfallFacade.random()).thenReturn(card);

        var random = scryfallService.random();
        assertNotNull(random);
        assertNotNull(random.id());
        assertEquals(id, random.id());

        verify(scryfallFacade).random();
    }

    @Test
    public void givenQueryThatMatchesNoCardThenAutocompleteReturnsEmptyListOfCards() {
        var scryfallAutoCompleteDto = new ScryfallAutoCompleteDto(0, List.of());

        var query = "nonexistingcard";

        when(scryfallFacade.autocomplete(query)).thenReturn(scryfallAutoCompleteDto);

        var result = scryfallService.autocomplete(query);
        assertNotNull(result);
        var autoCompleteCards = result.data();
        assertNotNull(autoCompleteCards);
        assertTrue(autoCompleteCards.isEmpty());

        verify(scryfallFacade).autocomplete(query);
    }

    @Test
    public void givenQueryThatMatchesCardsThenAutocompleteReturnsListOfCards() {
        var query = "Well";

        var cardNames = List.of("Well Done", "Wellspring", "Wellwisher", "Farewell");
        var autocomplete = new ScryfallAutoCompleteDto(cardNames.size(), cardNames);

        when(scryfallFacade.autocomplete(query)).thenReturn(autocomplete);

        var result = scryfallService.autocomplete(query);
        assertNotNull(result);
        var autoCompleteCards = result.data();
        assertNotNull(autoCompleteCards);
        assertFalse(autoCompleteCards.isEmpty());
        // Check that the query is a substring from all the cards
        autoCompleteCards.forEach(card -> assertTrue(card.toLowerCase().contains(query.toLowerCase())));

        verify(scryfallFacade).autocomplete(query);
    }

    @Test
    public void givenAnIdThatDoesNotMatchACardThenGetCardByIdReturns404() {
        var id = "123321";
        String expectedMessage = "Card not found";

        when(scryfallFacade.getCardById(id)).thenThrow(
                new ScryfallIntegrationException(HttpStatus.NOT_FOUND, expectedMessage));

        var exception = assertThrows(ScryfallIntegrationException.class, () -> scryfallService.getCardById(id));
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        verify(scryfallFacade).getCardById(id);
    }

    @Test
    public void givenValidIdThenGetCardByIdReturnsCardData() {
        var id = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        var name = "Wellwisher";
        var scryfallCard = new ScryfallCardDto.ScryfallCardDtoBuilder().id(id).name(name).build();

        when(scryfallFacade.getCardById(id)).thenReturn(scryfallCard);

        var result = scryfallService.getCardById(id);
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(id, result.id());
        assertEquals(name, result.name());

        verify(scryfallFacade).getCardById(id);
    }

    @Test
    public void givenQueryThenSearchReturnsCorrectResults() {
        var id = "19dbbecb-b4d0-49d2-b36e-58279e051c5c";
        var name = "Wellwisher";
        var scryfallCard = new ScryfallCardDto.ScryfallCardDtoBuilder().id(id).name(name).build();

        var page = 1;
        var query = "wellwisher";
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().query(query).build();

        var scryfallCards = List.of(scryfallCard);
        var scryfallSearchResult = new ScryfallSearchCardResultDto(scryfallCards.size(), false, scryfallCards);

        when(scryfallFacade.search(filter, page)).thenReturn(scryfallSearchResult);

        var result = scryfallService.search(filter, page);
        assertNotNull(result);
        var cards = result.data();
        var length = result.totalCards();
        assertEquals(scryfallCards.size(), length);
        var card = cards.getFirst();
        assertEquals(id, card.id());
        assertEquals(name, card.name());

        verify(scryfallFacade).search(filter, page);
    }
}
