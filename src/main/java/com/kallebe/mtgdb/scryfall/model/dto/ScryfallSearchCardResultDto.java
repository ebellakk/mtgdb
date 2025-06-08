package com.kallebe.mtgdb.scryfall.model.dto;

import java.util.List;

public record ScryfallSearchCardResultDto(Integer total_cards, Boolean has_more, List<ScryfallCardDto> data) {
}
