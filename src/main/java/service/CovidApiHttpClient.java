package service;

import model.CountryDailyData;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

class CovidApiHttpClient {

    private final static String API_URL_TEMPLATE = "https://api.covid19api.com/country/%s?from=%sT00:00:00Z&to=%sT00:00:00Z";

    private final static String TODAY = new Date().toInstant().toString().substring(0, 10);

    private static String getUri(String country, String fromDate, String toDate) {
        return String.format(API_URL_TEMPLATE, country, fromDate, toDate);
    }

    private static URI getUriForCountry(String country, String fromDate) {
        String uri = getUri(country, fromDate, TODAY);
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    static List<CountryDailyData> getStatForAll(List<String> countries, String fromDate) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        return countries.stream()
                .map(c -> getUriForCountry(c, fromDate))
                .filter(Objects::nonNull)
                .map(url -> httpClient.sendAsync(HttpRequest.newBuilder(url).GET().build(),
                        HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(ModelMapper::parseApiResponse))
                .filter(Objects::nonNull)
                .map(completableFuture -> {
                    try {
                        return completableFuture.get(5, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(data -> data.length > 0)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
