package com.kallebe.mtgdb.scryfall.model.dto;

public record ScryfallImageDto(String normal, String art_crop, String border_crop) {
    public static final class ScryfallImageDtoBuilder {
        String normal;
        String art_crop;
        String border_crop;

        public ScryfallImageDtoBuilder normal(String normal) {
            this.normal = normal;
            return this;
        }

        public ScryfallImageDtoBuilder art_crop(String art_crop) {
            this.art_crop = art_crop;
            return this;
        }

        public ScryfallImageDtoBuilder border_crop(String border_crop) {
            this.border_crop = border_crop;
            return this;
        }

        public ScryfallImageDto build() {
            return new ScryfallImageDto(normal, art_crop, border_crop);
        }
    }
}
