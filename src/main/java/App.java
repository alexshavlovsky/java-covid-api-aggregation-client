import service.CovidAggregationFacade;

import java.util.Arrays;
import java.util.List;

public class App {
    private final static List<String> W_EUROPE = Arrays.asList("spain", "germany", "austria", "netherlands", "belgium", "switzerland");
    private final static List<String> E_EUROPE = Arrays.asList("belarus", "poland", "ukraine", "russia", "latvia", "lithuania");

    public static void main(String[] args) {
        CovidAggregationFacade.printReportForCountries(W_EUROPE, "2020-03-07");
        CovidAggregationFacade.printReportForCountries(E_EUROPE, "2020-03-21");
    }

}
