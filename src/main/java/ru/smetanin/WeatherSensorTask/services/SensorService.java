package ru.smetanin.WeatherSensorTask.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.smetanin.WeatherSensorTask.dto.SensorDTO;
import ru.smetanin.WeatherSensorTask.utils.SensorException;
import ru.smetanin.WeatherSensorTask.models.Sensor;
import ru.smetanin.WeatherSensorTask.repositories.SensorRepository;
import ru.smetanin.WeatherSensorTask.utils.SensorBindingException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SensorService
{
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    private final SensorValidationService sensorValidationService;

    public Sensor getSensor(String name)
    {
        Optional<Sensor> optSensor = sensorRepository.findByName(name);
        return optSensor.get();
    }

    @Transactional
    public Sensor getSensorByDTO(SensorDTO sensorDTO)
    {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @Transactional(rollbackFor = SensorException.class)
    public void registerSensor(SensorDTO sensorDTO, BindingResult bindingResult) throws SensorException,
            SensorBindingException
    {
        Sensor sensor = getSensorByDTO(sensorDTO);
        registerSensor(sensor, bindingResult);
        sensorRepository.save(sensor);
    }

    @Transactional(rollbackFor = SensorException.class)
    public void registerSensor(Sensor sensor, BindingResult bindingResult) throws SensorBindingException,
            SensorException
    {
        sensorValidationService.checkSensorBindings(bindingResult);
        registerSensor(sensor);
    }

    @Transactional(rollbackFor = SensorException.class)
    public void registerSensor(SensorDTO sensorDTO) throws SensorException
    {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        registerSensor(sensor);
    }

    @Transactional(rollbackFor = SensorException.class)
    public void registerSensor(Sensor sensor) throws SensorException
    {
        sensorValidationService.checkSensorRegisterPossibility(sensor);
        sensorRepository.save(sensor);
    }
}
