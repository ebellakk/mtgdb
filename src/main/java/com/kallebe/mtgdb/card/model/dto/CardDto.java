package com.kallebe.mtgdb.card.model.dto;

import java.util.List;

public record CardDto(String id,
                      String oracleId,
                      String name,
                      String releasedAt,
                      String uri,
                      String scryfallUri,
                      ImageDto imageUris,
                      String manaCost,
                      Integer cmc,
                      String typeLine,
                      String oracleText,
                      String power,
                      String toughness,
                      List<String> colors,
                      List<String> colorIdentity,
                      List<String> keywords,
                      String set,
                      String setId,
                      String setName,
                      String flavorText,
                      String artist,
                      RelatedUriDto relatedUris) {

    public static final class CardDtoBuilder {
        String id;
        String oracleId;
        String name;
        String releasedAt;
        String uri;
        String scryfallUri;
        ImageDto imageUris;
        String manaCost;
        Integer cmc;
        String typeLine;
        String oracleText;
        String power;
        String toughness;
        List<String> colors;
        List<String> colorIdentity;
        List<String> keywords;
        String set;
        String setId;
        String setName;
        String flavorText;
        String artist;
        RelatedUriDto relatedUris;

        public CardDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CardDtoBuilder oracleId(String oracleId) {
            this.oracleId = oracleId;
            return this;
        }

        public CardDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CardDtoBuilder releasedAt(String releasedAt) {
            this.releasedAt = releasedAt;
            return this;
        }

        public CardDtoBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public CardDtoBuilder scryfallUri(String scryfallUri) {
            this.scryfallUri = scryfallUri;
            return this;
        }

        public CardDtoBuilder imageUris(ImageDto imageUris) {
            this.imageUris = imageUris;
            return this;
        }

        public CardDtoBuilder manaCost(String manaCost) {
            this.manaCost = manaCost;
            return this;
        }

        public CardDtoBuilder cmc(Integer cmc) {
            this.cmc = cmc;
            return this;
        }

        public CardDtoBuilder typeLine(String typeLine) {
            this.typeLine = typeLine;
            return this;
        }

        public CardDtoBuilder oracleText(String oracleText) {
            this.oracleText = oracleText;
            return this;
        }

        public CardDtoBuilder power(String power) {
            this.power = power;
            return this;
        }

        public CardDtoBuilder toughness(String toughness) {
            this.toughness = toughness;
            return this;
        }

        public CardDtoBuilder colors(List<String> colors) {
            this.colors = colors;
            return this;
        }

        public CardDtoBuilder colorIdentity(List<String> colorIdentity) {
            this.colorIdentity = colorIdentity;
            return this;
        }

        public CardDtoBuilder keywords(List<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public CardDtoBuilder set(String set) {
            this.set = set;
            return this;
        }

        public CardDtoBuilder setId(String setId) {
            this.setId = setId;
            return this;
        }

        public CardDtoBuilder setName(String setName) {
            this.setName = setName;
            return this;
        }

        public CardDtoBuilder flavorText(String flavorText) {
            this.flavorText = flavorText;
            return this;
        }

        public CardDtoBuilder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public CardDtoBuilder relatedUris(RelatedUriDto relatedUris) {
            this.relatedUris = relatedUris;
            return this;
        }

        public CardDto build() {
            return new CardDto(id, oracleId, name, releasedAt, uri, scryfallUri, imageUris, manaCost, cmc,
                    typeLine, oracleText, power, toughness, colors, colorIdentity, keywords,
                    set, setId, setName, flavorText, artist, relatedUris);
        }

    }
}
