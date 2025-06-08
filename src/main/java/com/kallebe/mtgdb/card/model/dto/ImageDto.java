package com.kallebe.mtgdb.card.model.dto;

public record ImageDto(String normal, String artCrop, String borderCrop) {
    public static final class ImageDtoBuilder {
        String normal;
        String artCrop;
        String borderCrop;

        public ImageDtoBuilder normal(String normal) {
            this.normal = normal;
            return this;
        }

        public ImageDtoBuilder artCrop(String artCrop) {
            this.artCrop = artCrop;
            return this;
        }

        public ImageDtoBuilder borderCrop(String borderCrop) {
            this.borderCrop = borderCrop;
            return this;
        }

        public ImageDto build() {
            return new ImageDto(normal, artCrop, borderCrop);
        }
    }
}
