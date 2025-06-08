package com.kallebe.mtgdb.card.model.dto;

public record RelatedUriDto(String gatherer) {
    public static final class RelatedUriDtoBuilder {
        String gatherer;

        public RelatedUriDtoBuilder gatherer(String gatherer) {
            this.gatherer = gatherer;
            return this;
        }

        public RelatedUriDto build() {
            return new RelatedUriDto(gatherer);
        }
    }
}
