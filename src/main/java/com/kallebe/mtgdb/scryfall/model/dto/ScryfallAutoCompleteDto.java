package com.kallebe.mtgdb.scryfall.model.dto;

import java.util.List;

public record ScryfallAutoCompleteDto(Integer total_values, List<String> data) {
}
