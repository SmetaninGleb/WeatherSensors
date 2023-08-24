package ru.smetanin.WeatherSensorTask.utils;

import lombok.Data;

public class SensorException extends Exception
{
    private String message;

    public SensorException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
