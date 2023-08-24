package ru.smetanin.WeatherSensorTask.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.smetanin.WeatherSensorTask.dto.MeasurementDTO;
import ru.smetanin.WeatherSensorTask.dto.SensorDTO;
import ru.smetanin.WeatherSensorTask.models.Measurement;
import ru.smetanin.WeatherSensorTask.models.Sensor;
import ru.smetanin.WeatherSensorTask.repositories.MeasurementRepository;
import ru.smetanin.WeatherSensorTask.repositories.SensorRepository;
import ru.smetanin.WeatherSensorTask.utils.MeasurementException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MeasurementService
{
    private final ModelMapper modelMapper;
    private final MeasurementValidationService measurementValidationService;
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Transactional
    public void addMeasurement(MeasurementDTO measurementDTO, BindingResult bindingResult)
            throws MeasurementException
    {
        measurementValidationService.checkMeasurementBindings(bindingResult);
        addMeasurement(measurementDTO);
    }

    @Transactional
    public void addMeasurement(MeasurementDTO measurementDTO) throws MeasurementException
    {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setTime(new Date(System.currentTimeMillis()));
        SensorDTO sensorDTO = measurementDTO.getSensor();
        Sensor sensor = sensorRepository
                .findByName(sensorDTO.getName())
                .orElseThrow(() -> new MeasurementException("No such sensor!"));
        measurement.setSensor(sensor);
        addMeasurement(measurement);
    }

    @Transactional
    public void addMeasurement(Measurement measurement) throws MeasurementException
    {
        measurementValidationService.checkMeasurementSensor(measurement);
        measurementRepository.save(measurement);
    }

    @Transactional
    public List<MeasurementDTO> getAllMeasurement()
    {
        List<Measurement> measurements = measurementRepository.findAll();
        return measurements.stream()
                .map(m -> modelMapper.map(m, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long getRainyDaysCount()
    {
        List<Measurement> measurements = measurementRepository.findAll();
        return measurements.stream()
                .filter(Measurement::getRaining)
                .count();
    }
}
