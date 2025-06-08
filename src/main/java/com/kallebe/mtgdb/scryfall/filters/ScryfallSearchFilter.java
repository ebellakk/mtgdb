package com.kallebe.mtgdb.scryfall.filters;

import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import org.springframework.http.HttpStatus;

import java.util.List;

public record ScryfallSearchFilter(String fullQuery, String query,
                                   String mana,
                                   String manaOperator,
                                   List<String> colors,
                                   List<String> colorOperators,
                                   String notColor,
                                   List<String> types,
                                   List<String> notTypes,
                                   String oracle,
                                   String keyword,
                                   String power,
                                   String powerOperator,
                                   String toughness,
                                   String toughnessOperator,
                                   String set) {

    public String generateQueryParametersString() {
        StringBuilder queryParametersStringBuilder = new StringBuilder();
        if ((fullQuery == null || fullQuery.isBlank()) && (query == null || query.isBlank())) {
            throw new ScryfallIntegrationException(HttpStatus.BAD_REQUEST,
                    "At least one of 'query' or 'fullQuery' must be provided");
        }
        if (fullQuery != null && !fullQuery.isBlank()) {
            queryParametersStringBuilder.append(String.format("%s", fullQuery));
            return queryParametersStringBuilder.toString().trim();
        }
        if (query != null && !query.isBlank()) {
            queryParametersStringBuilder.append(String.format("%s", query));
        }
        if (mana != null && !mana.isBlank()) {
            var usedManaOperator = manaOperator != null ? manaOperator : ":";
            queryParametersStringBuilder.append(String.format(" mana%s%s", usedManaOperator, mana));
        }
        if (colors != null && !colors.isEmpty()) {
            StringBuilder colorsSubFilterStringBuilder = new StringBuilder();
            colorsSubFilterStringBuilder.append(" (");
            for (int i = 0; i < colors.size(); i++) {
                var color = colors.get(i);
                var colorOperator = colorOperators != null && colorOperators.size() > i ? colorOperators.get(i) : null;
                var usedColorOperator = colorOperator != null ? colorOperator : ":";
                colorsSubFilterStringBuilder.append(String.format("color%s%s ", usedColorOperator, color));
                if (i < (colors.size() - 1)) {
                    colorsSubFilterStringBuilder.append("or ");
                }
            }
            var colorSubfilterString = colorsSubFilterStringBuilder.toString().stripTrailing();
            queryParametersStringBuilder.append(colorSubfilterString);
            queryParametersStringBuilder.append(")");
        }
        if (notColor != null && !notColor.isBlank()) {
            queryParametersStringBuilder.append(String.format(" -color:%s", notColor));
        }
        if (types != null && !types.isEmpty()) {
            StringBuilder typesSubFilterStringBuilder = new StringBuilder();
            typesSubFilterStringBuilder.append(" (");
            for (int i = 0; i < types.size(); i++) {
                var type = types.get(i);
                typesSubFilterStringBuilder.append(String.format("type:%s ", type));
                if (i < (types.size() - 1)) {
                    typesSubFilterStringBuilder.append("or ");
                }
            }
            var typeSubfilterString = typesSubFilterStringBuilder.toString().stripTrailing();
            queryParametersStringBuilder.append(typeSubfilterString);
            queryParametersStringBuilder.append(")");
        }
        if (notTypes != null && !notTypes.isEmpty()) {
            for (var notType : notTypes) {
                queryParametersStringBuilder.append(String.format(" -type:%s", notType));
            }
        }
        if (oracle != null && !oracle.isBlank()) {
            queryParametersStringBuilder.append(String.format(" oracle:%s", oracle));
        }
        if (keyword != null && !keyword.isBlank()) {
            queryParametersStringBuilder.append(String.format(" keyword:%s", keyword));
        }
        if (power != null && !power.isBlank()) {
            var usedPowerOperator = powerOperator != null ? powerOperator : ":";
            queryParametersStringBuilder.append(String.format(" power%s%s", usedPowerOperator, power));
        }
        if (toughness != null && !toughness.isBlank()) {
            var usedToughnessOperator = toughnessOperator != null ? toughnessOperator : ":";
            queryParametersStringBuilder.append(String.format(" toughness%s%s", usedToughnessOperator, toughness));
        }
        if (set != null && !set.isBlank()) {
            queryParametersStringBuilder.append(String.format(" set:%s", set));
        }
        return queryParametersStringBuilder.toString().trim();
    }

    public static final class ScryfallSearchFilterBuilder {
        String fullQuery;
        String query;
        String mana;
        String manaOperator;
        List<String> colors;
        List<String> colorOperators;
        String notColor;
        List<String> types;
        List<String> notTypes;
        String oracle;
        String keyword;
        String power;
        String powerOperator;
        String toughness;
        String toughnessOperator;
        String set;

        public ScryfallSearchFilterBuilder fullQuery(String fullQuery) {
            this.fullQuery = fullQuery;
            return this;
        }

        public ScryfallSearchFilterBuilder query(String query) {
            this.query = query;
            return this;
        }

        public ScryfallSearchFilterBuilder mana(String mana) {
            this.mana = mana;
            return this;
        }

        public ScryfallSearchFilterBuilder manaOperator(String manaOperator) {
            this.manaOperator = manaOperator;
            return this;
        }

        public ScryfallSearchFilterBuilder colors(List<String> colors) {
            this.colors = colors;
            return this;
        }

        public ScryfallSearchFilterBuilder colorOperators(List<String> colorOperators) {
            this.colorOperators = colorOperators;
            return this;
        }

        public ScryfallSearchFilterBuilder notColor(String notColor) {
            this.notColor = notColor;
            return this;
        }

        public ScryfallSearchFilterBuilder types(List<String> types) {
            this.types = types;
            return this;
        }

        public ScryfallSearchFilterBuilder notTypes(List<String> notTypes) {
            this.notTypes = notTypes;
            return this;
        }

        public ScryfallSearchFilterBuilder oracle(String oracle) {
            this.oracle = oracle;
            return this;
        }

        public ScryfallSearchFilterBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public ScryfallSearchFilterBuilder power(String power) {
            this.power = power;
            return this;
        }

        public ScryfallSearchFilterBuilder powerOperator(String powerOperator) {
            this.powerOperator = powerOperator;
            return this;
        }

        public ScryfallSearchFilterBuilder toughness(String toughness) {
            this.toughness = toughness;
            return this;
        }

        public ScryfallSearchFilterBuilder toughnessOperator(String toughnessOperator) {
            this.toughnessOperator = toughnessOperator;
            return this;
        }

        public ScryfallSearchFilterBuilder set(String set) {
            this.set = set;
            return this;
        }

        public ScryfallSearchFilter build() {
            return new ScryfallSearchFilter(fullQuery, query, mana, manaOperator, colors, colorOperators, notColor,
                    types,
                    notTypes, oracle, keyword, power, powerOperator, toughness, toughnessOperator, set);
        }
    }
}
