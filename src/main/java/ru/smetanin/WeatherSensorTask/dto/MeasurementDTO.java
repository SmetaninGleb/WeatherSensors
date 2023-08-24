package ru.smetanin.WeatherSensorTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MeasurementDTO
{
//    @JsonProperty(value = "raining")
    @NotNull
    private Boolean raining;

    @Min(value = -100, message = "Temperature range should be from -100 to 100!")
    @Max(value = 100, message = "Temperature range should be from -100 to 100!")
    @NotNull
    private Float value;

    @NotNull
    private SensorDTO sensor;
}
