package com.kallebe.mtgdb.scryfall.model.dto;

public record ScryfallRelatedUriDto(String gatherer) {
    public static final class ScryfallRelatedUriDtoBuilder {
        String gatherer;

        public ScryfallRelatedUriDtoBuilder gatherer(String gatherer) {
            this.gatherer = gatherer;
            return this;
        }

        public ScryfallRelatedUriDto build() {
            return new ScryfallRelatedUriDto(gatherer);
        }
    }
}
