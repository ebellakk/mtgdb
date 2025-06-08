package com.kallebe.mtgdb.card.model.dto;

import java.util.List;

public record AutoCompleteDto(Integer totalCards, List<String> data) {
}
