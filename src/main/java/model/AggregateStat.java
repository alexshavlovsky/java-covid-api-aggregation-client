package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregateStat {
    private int confirmed;
    private int deaths;

    public static AggregateStat fromCountryDailyData(CountryDailyData countryDailyData) {
        return new AggregateStat(countryDailyData.getConfirmed(), countryDailyData.getDeaths());
    }

    public static AggregateStat add(AggregateStat d1, AggregateStat d2) {
        return new AggregateStat(
                d1.getConfirmed() + d2.getConfirmed(),
                d1.getDeaths() + d2.getDeaths()
        );
    }
}
