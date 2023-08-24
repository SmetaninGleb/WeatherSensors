package ru.smetanin.WeatherSensorTask.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.smetanin.WeatherSensorTask.models.Sensor;
import ru.smetanin.WeatherSensorTask.repositories.SensorRepository;
import ru.smetanin.WeatherSensorTask.utils.SensorException;
import ru.smetanin.WeatherSensorTask.utils.SensorBindingException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SensorValidationService
{
    private final SensorRepository sensorRepository;

    public void checkSensorBindings(BindingResult bindingResult) throws SensorBindingException
    {
        if (bindingResult.hasErrors())
        {
            throw new SensorBindingException(bindingResult);
        }
    }

    @Transactional
    public void checkSensorRegisterPossibility(Sensor sensor) throws SensorException
    {
        Optional<Sensor> optSensor = sensorRepository.findByName(sensor.getName());
        if (optSensor.isPresent())
            throw new SensorException("Such sensor already exist!");
    }
}
