package com.kallebe.mtgdb.scryfall.facade;

import com.kallebe.mtgdb.error.ScryfallErrorDto;
import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import com.kallebe.mtgdb.scryfall.filters.ScryfallSearchFilter;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallAutoCompleteDto;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallCardDto;
import com.kallebe.mtgdb.scryfall.model.dto.ScryfallSearchCardResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Service
public class ScryfallFacade {

    @Value("${scryfall.base.url}")
    private String baseUrl;

    private RestClient.RequestHeadersSpec<?> createPartialGetRequest(RestClient restClient, String uri) {
        return restClient.get().uri(uri).header("User-Agent", "mtgdb/1.0")
                .header("Accept", "application/json;q=0.9,*/*;q=0.8");
    }

    private void handleErrorIfPresent(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) throws
            IOException {
        if (response.getStatusCode().is4xxClientError()) {
            var scryfallError = response.bodyTo(ScryfallErrorDto.class);
            throw new ScryfallIntegrationException(HttpStatus.resolve(response.getStatusCode().value()),
                    scryfallError.details());
        }
    }

    public ScryfallCardDto random() {
        RestClient restClient = RestClient.create();
        var uri = baseUrl + "/cards/random";
        var partialRequest = this.createPartialGetRequest(restClient, uri);
        return partialRequest.retrieve().body(ScryfallCardDto.class);
    }

    public ScryfallAutoCompleteDto autocomplete(String query) {
        RestClient restClient = RestClient.create();
        var uri = baseUrl + "/cards/autocomplete?q=" + query;
        var partialRequest = this.createPartialGetRequest(restClient, uri);
        return partialRequest.exchange((request, response) -> {
            this.handleErrorIfPresent(response);
            return response.bodyTo(ScryfallAutoCompleteDto.class);
        });
    }

    public ScryfallSearchCardResultDto search(ScryfallSearchFilter searchFilters, Integer page) {
        int usedPage = page != null ? page : 1;
        RestClient restClient = RestClient.create();
        var searchFilterQueryParameters = searchFilters.generateQueryParametersString();
        var finalQuery = String.format("%s", searchFilterQueryParameters);
        var uri = baseUrl + "/cards/search?q=" + finalQuery + "&page=" + usedPage;
        var partialRequest = this.createPartialGetRequest(restClient, uri);
        return partialRequest.exchange((request, response) -> {
            this.handleErrorIfPresent(response);
            return response.bodyTo(ScryfallSearchCardResultDto.class);
        });
    }

    public ScryfallCardDto getCardById(String uuid) {
        RestClient restClient = RestClient.create();
        var uri = baseUrl + "/cards/" + uuid;
        var partialRequest = this.createPartialGetRequest(restClient, uri);
        return partialRequest.exchange((request, response) -> {
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ScryfallIntegrationException(HttpStatus.resolve(response.getStatusCode().value()),
                        "Card not found");
            }
            this.handleErrorIfPresent(response);
            return response.bodyTo(ScryfallCardDto.class);
        });
    }
}
