package co.mz.vodafone.TravelApp.dtos;

import lombok.*;

import java.util.List;

@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherData {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;

}

