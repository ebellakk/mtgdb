package com.kallebe.mtgdb.scryfall.model.dto;

import java.util.List;

public record ScryfallCardDto(String id,
                              String oracle_id,
                              String name,
                              String released_at,
                              String uri,
                              String scryfall_uri,
                              ScryfallImageDto image_uris,
                              String mana_cost,
                              Integer cmc,
                              String type_line,
                              String oracle_text,
                              String power,
                              String toughness,
                              List<String> colors,
                              List<String> color_identity,
                              List<String> keywords,
                              String set,
                              String set_id,
                              String set_name,
                              String flavor_text,
                              String artist,
                              ScryfallRelatedUriDto related_uris) {
    public static final class ScryfallCardDtoBuilder {
        String id;
        String oracle_id;
        String name;
        String released_at;
        String uri;
        String scryfall_uri;
        ScryfallImageDto image_uris;
        String mana_cost;
        Integer cmc;
        String type_line;
        String oracle_text;
        String power;
        String toughness;
        List<String> colors;
        List<String> colors_identity;
        List<String> keywords;
        String set;
        String set_id;
        String set_name;
        String flavor_text;
        String artist;
        ScryfallRelatedUriDto related_uris;

        public ScryfallCardDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ScryfallCardDtoBuilder oracle_id(String oracle_id) {
            this.oracle_id = oracle_id;
            return this;
        }

        public ScryfallCardDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ScryfallCardDtoBuilder released_at(String released_at) {
            this.released_at = released_at;
            return this;
        }

        public ScryfallCardDtoBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public ScryfallCardDtoBuilder scryfall_uri(String scryfall_uri) {
            this.scryfall_uri = scryfall_uri;
            return this;
        }

        public ScryfallCardDtoBuilder image_uris(ScryfallImageDto image_uris) {
            this.image_uris = image_uris;
            return this;
        }

        public ScryfallCardDtoBuilder mana_cost(String mana_cost) {
            this.mana_cost = mana_cost;
            return this;
        }

        public ScryfallCardDtoBuilder cmc(Integer cmc) {
            this.cmc = cmc;
            return this;
        }

        public ScryfallCardDtoBuilder type_line(String type_line) {
            this.type_line = type_line;
            return this;
        }

        public ScryfallCardDtoBuilder oracle_text(String oracle_text) {
            this.oracle_text = oracle_text;
            return this;
        }

        public ScryfallCardDtoBuilder power(String power) {
            this.power = power;
            return this;
        }

        public ScryfallCardDtoBuilder toughness(String toughness) {
            this.toughness = toughness;
            return this;
        }

        public ScryfallCardDtoBuilder colors(List<String> colors) {
            this.colors = colors;
            return this;
        }

        public ScryfallCardDtoBuilder color_identity(List<String> color_identity) {
            this.colors_identity = color_identity;
            return this;
        }

        public ScryfallCardDtoBuilder keywords(List<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public ScryfallCardDtoBuilder set(String set) {
            this.set = set;
            return this;
        }

        public ScryfallCardDtoBuilder set_id(String set_id) {
            this.set_id = set_id;
            return this;
        }

        public ScryfallCardDtoBuilder set_name(String set_name) {
            this.set_name = set_name;
            return this;
        }

        public ScryfallCardDtoBuilder flavor_text(String flavor_text) {
            this.flavor_text = flavor_text;
            return this;
        }

        public ScryfallCardDtoBuilder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public ScryfallCardDtoBuilder related_uris(ScryfallRelatedUriDto related_uris) {
            this.related_uris = related_uris;
            return this;
        }

        public ScryfallCardDto build() {
            return new ScryfallCardDto(id, oracle_id, name, released_at, uri, scryfall_uri, image_uris, mana_cost,
                    cmc, type_line, oracle_text, power, toughness, colors, colors_identity, keywords, set, set_id,
                    set_name, flavor_text, artist, related_uris);
        }
    }
}
