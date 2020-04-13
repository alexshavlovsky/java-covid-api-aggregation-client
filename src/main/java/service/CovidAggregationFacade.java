package service;

import model.AggregateStat;
import model.CountryDailyData;

import java.util.*;
import java.util.stream.Collectors;

public class CovidAggregationFacade {

    public static void printReportForCountries(List<String> countries, String fromDate) {
        List<CountryDailyData> response = CovidApiHttpClient.getStatForAll(countries, fromDate);

        Map<String, List<CountryDailyData>> groupingByCountry = response.stream().collect(Collectors.groupingBy(CountryDailyData::getCountry));

        Map<Date, AggregateStat> aggregateStat = response.stream()
                .collect(Collectors.toMap(
                        CountryDailyData::getDate,
                        AggregateStat::fromCountryDailyData,
                        AggregateStat::add
                        )
                );
        System.out.println("==========================");
        System.out.println("COVID-19 data aggregation:");

        groupingByCountry.keySet().stream()
                .sorted()
                .map(country -> String.format("%s: %d entries", country, groupingByCountry.get(country).size()))
                .forEach(System.out::println);

        List<List<String>> table = new ArrayList<>();
        table.add(Arrays.asList("Date", "Cases", "Deaths"));
        aggregateStat
                .keySet()
                .stream()
                .sorted()
                .forEach(x -> table.add(Arrays.asList(x.toInstant().toString().substring(0, 10), Integer.toString(aggregateStat.get(x).getConfirmed()), Integer.toString(aggregateStat.get(x).getDeaths()))));

        System.out.println("--------------------------");
        System.out.println(new TextTableFormatter(table));
    }
}
