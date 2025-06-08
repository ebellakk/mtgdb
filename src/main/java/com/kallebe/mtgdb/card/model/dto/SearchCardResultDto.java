package com.kallebe.mtgdb.card.model.dto;

import java.util.List;

public record SearchCardResultDto(Integer totalCards, Boolean hasMore, List<CardDto> data) {
}
