package ru.smetanin.WeatherSensorTask.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class SensorBindingException extends Exception
{
    private BindingResult bindingResult;
}
