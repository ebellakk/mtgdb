package com.kallebe.mtgdb.card.controllers;

import com.kallebe.mtgdb.card.model.dto.AutoCompleteDto;
import com.kallebe.mtgdb.card.model.dto.CardDto;
import com.kallebe.mtgdb.card.model.dto.SearchCardResultDto;
import com.kallebe.mtgdb.scryfall.filters.ScryfallSearchFilter;
import com.kallebe.mtgdb.scryfall.service.ScryfallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cards")
public class CardsController {
    private final ScryfallService scryfallService;

    public CardsController(ScryfallService scryfallService) {
        this.scryfallService = scryfallService;
    }

    @Tag(name = "Random Card")
    @Operation(summary = "Get a random card")
    @GetMapping("/random")
    public CardDto random() {
        return scryfallService.random();
    }

    @Tag(name = "Autocomplete")
    @Operation(summary = "Get an autocomplete for cards that contain the provided query in its title")
    @GetMapping("/autocomplete")
    public AutoCompleteDto autocomplete(
            @RequestParam @Parameter(name = "query", description = "Query", example = "Well") String query) {
        return scryfallService.autocomplete(query);
    }

    @Tag(name = "Search")
    @Operation(summary = "Search for a card on scryfall api. See scryfall api docs for more information")
    @GetMapping("/search")
    public SearchCardResultDto search(
            @RequestParam(required = false) @Parameter(name = "fullQuery", description = "Scryfall full query. " +
                    "Example: krenko mob type:goblin type:legend mana:2RR") String fullQuery,
            @RequestParam(required = false) @Parameter(name = "query", description = "Query that will be used on " +
                    "full-text") String query,
            @RequestParam(required = false) @Parameter(name = "page", description = "Page to fetch on pagination. " +
                    "Default: 1") Integer page,
            @RequestParam(required = false) @Parameter(name = "mana", description = "Mana filter") String mana,
            @RequestParam(required = false) @Parameter(name = "manaOperator", description = "Mana filter operator. " +
                    "Example: > >= < <=. If not provided, : will be used") String manaOperator,
            @RequestParam(required = false) @Parameter(name = "colors", description = "List of colors to filter") List<String> colors,
            @RequestParam(required = false) @Parameter(name = "colorOperators", description = "Operators of the " +
                    "colors filter. Example: > >= < <=. If not provided, : will be used") List<String> colorOperators,
            @RequestParam(required = false) String notColor,
            @RequestParam(required = false) @Parameter(name = "types", description = "Card types (creature, instant " +
                    "etc.)") List<String> types,
            @RequestParam(required = false) @Parameter(name = "notTypes", description = "Types to be excluded from " +
                    "search") List<String> notTypes,
            @RequestParam(required = false) @Parameter(name = "oracle", description = "Oracle text search in card " +
                    "text box. Example: draw") String oracle,
            @RequestParam(required = false) @Parameter(name = "keyword", description = "Keyword ability. Example: " +
                    "flying") String keyword,
            @RequestParam(required = false) @Parameter(name = "power", description = "Power of the card") String power,
            @RequestParam(required = false) @Parameter(name = "powerOperator", description = "Operator of the power " +
                    "filter. Example: > >= < <=. If not provided, : will be used") String powerOperator,
            @RequestParam(required = false) @Parameter(name = "toughness", description = "Toughness of the card") String toughness,
            @RequestParam(required = false) @Parameter(name = "toughnessOperator", description = "Operator of the " +
                    "toughness " +
                    "filter. Example: > >= < <=. If not provided, : will be used") String toughnessOperator,
            @RequestParam(required = false) @Parameter(name = "set", description = "Set filter. Example: rna") String set) {
        var scryfallFilter = new ScryfallSearchFilter.ScryfallSearchFilterBuilder().fullQuery(fullQuery).query(query)
                .mana(mana)
                .manaOperator(manaOperator).colors(colors)
                .colorOperators(colorOperators)
                .notColor(notColor).types(types).notTypes(notTypes).oracle(oracle).keyword(keyword).power(power)
                .powerOperator(powerOperator)
                .toughness(toughness).toughnessOperator(toughnessOperator).set(set).build();
        return scryfallService.search(scryfallFilter, page);
    }

    @Tag(name = "Get card")
    @Operation(summary = "Get a card by its uuid")
    @GetMapping("/{uuid}")
    public CardDto getCardById(
            @PathVariable @Parameter(name = "uuid", description = "UUID of the card", example = "19dbbecb-b4d0-49d2" +
                    "-b36e-58279e051c5c") String uuid) {
        return scryfallService.getCardById(uuid);
    }
}
