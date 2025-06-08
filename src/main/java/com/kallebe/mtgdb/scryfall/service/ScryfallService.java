package com.kallebe.mtgdb.scryfall.service;

import com.kallebe.mtgdb.card.model.dto.*;
import com.kallebe.mtgdb.scryfall.facade.ScryfallFacade;
import com.kallebe.mtgdb.scryfall.filters.ScryfallSearchFilter;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallCardDto;
import org.springframework.stereotype.Service;

@Service
public class ScryfallService {

    private final ScryfallFacade scryfallFacade;

    public ScryfallService(ScryfallFacade scryfallFacade) {
        this.scryfallFacade = scryfallFacade;
    }

    private CardDto fromScryfallCardDto(ScryfallCardDto card) {
        if (card == null) {
            return null;
        }
        var imageUris = card.image_uris() != null ? new ImageDto.ImageDtoBuilder().normal(card.image_uris().normal())
                .artCrop(card.image_uris().art_crop()).borderCrop(card.image_uris().border_crop()).build() : null;
        var relatedUris = card.related_uris() != null ? new RelatedUriDto.RelatedUriDtoBuilder().gatherer(
                card.related_uris().gatherer()).build() : null;
        return new CardDto.CardDtoBuilder().id(card.id()).oracleId(card.oracle_id()).name(card.name())
                .releasedAt(card.released_at()).uri(card.uri()).scryfallUri(card.scryfall_uri()).imageUris(imageUris)
                .manaCost(card.mana_cost()).cmc(card.cmc()).typeLine(card.type_line()).oracleText(card.oracle_text())
                .power(card.power())
                .toughness(card.toughness()).colors(card.colors()).colorIdentity(card.color_identity())
                .keywords(card.keywords()).set(card.set()).setId(card.set_id()).setName(card.set_name())
                .flavorText(card.flavor_text())
                .artist(card.artist()).relatedUris(relatedUris).build();
    }

    public CardDto random() {
        var randomCard = this.scryfallFacade.random();
        return fromScryfallCardDto(randomCard);
    }

    public AutoCompleteDto autocomplete(String query) {
        var autoComplete = this.scryfallFacade.autocomplete(query);
        return new AutoCompleteDto(autoComplete.total_values(), autoComplete.data());
    }

    public SearchCardResultDto search(ScryfallSearchFilter searchFilters, Integer page) {
        var searchResult = this.scryfallFacade.search(searchFilters, page);
        return new SearchCardResultDto(searchResult.total_cards(), searchResult.has_more(),
                searchResult.data().stream().map(this::fromScryfallCardDto).toList());
    }

    public CardDto getCardById(String uuid) {
        var card = this.scryfallFacade.getCardById(uuid);
        return fromScryfallCardDto(card);
    }
}
