package com.kallebe.mtgdb.scryfall.filters;

import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScryfallSearchFilterTest {

    @Test
    public void testGivenAllParametersThenGenerateQueryParametersStringShouldReturnFullQuery() {
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().query("dr").mana("r")
                .colors(List.of("r", "g"))
                .notColor("g").types(List.of("instant", "sorcery")).notTypes(List.of("creature")).oracle("play")
                .keyword("flying").power("4").toughness("4").set("woe").build();
        var generatedQuery = filter.generateQueryParametersString();
        var expected = "dr mana:r (color:r or color:g) -color:g (type:instant or type:sorcery) -type:creature " +
                "oracle:play keyword:flying power:4 toughness:4 set:woe";
        assertEquals(expected, generatedQuery);
    }

    @Test
    public void testGivenNoParametersThenGenerateQueryParametersStringShouldThrowBadRequestException() {
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().build();
        var expectedMessage = "At least one of 'query' or 'fullQuery' must be provided";
        var exception = assertThrows(ScryfallIntegrationException.class, filter::generateQueryParametersString);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testGivenOnlySomeParametersThenGenerateQueryParametersStringShouldReturnCorrectQuery() {
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().query("well")
                .colors(List.of("r", "uw", "g"))
                .colorOperators(List.of(":", ">="))
                .mana("2RR")
                .manaOperator(">=")
                .types(List.of("creature")).build();
        var generatedQuery = filter.generateQueryParametersString();
        var expected = "well mana>=2RR (color:r or color>=uw or color:g) (type:creature)";
        assertEquals(expected, generatedQuery);
    }

    @Test
    public void testGivenFullQueryThenGenerateQueryParametersStringShouldReturnTheSameFullQuery() {
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().fullQuery(
                "well mana:4RR color:r type:creature").build();
        var generatedQuery = filter.generateQueryParametersString();
        var expected = "well mana:4RR color:r type:creature";
        assertEquals(expected, generatedQuery);
    }

    @Test
    public void testGivenNoFullQueryOrQueryThenGenerateQueryParametersStringShouldThrowBadRequestException() {
        var filter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().power("4").build();
        var expectedMessage = "At least one of 'query' or 'fullQuery' must be provided";
        var exception = assertThrows(ScryfallIntegrationException.class, filter::generateQueryParametersString);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
