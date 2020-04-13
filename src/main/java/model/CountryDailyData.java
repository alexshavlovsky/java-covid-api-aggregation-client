package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDailyData {
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Confirmed")
    private int confirmed;
    @JsonProperty("Deaths")
    private int deaths;
//    @JsonProperty("Recovered")
//    private int recovered;
    @JsonProperty("Date")
    private Date date;
}
