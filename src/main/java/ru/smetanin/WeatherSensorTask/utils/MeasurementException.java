package ru.smetanin.WeatherSensorTask.utils;

import java.util.function.Supplier;

public class MeasurementException extends Exception
{
    public MeasurementException(String message)
    {
        super(message);
    }
}
