package ru.smetanin.WeatherSensorTask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SensorDTO
{
    @NotNull
    @Length(min = 3, max = 30, message = "Sensor name is not valid!")
    private String name;

}
