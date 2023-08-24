package ru.smetanin.WeatherSensorTask.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.smetanin.WeatherSensorTask.models.Measurement;
import ru.smetanin.WeatherSensorTask.models.Sensor;
import ru.smetanin.WeatherSensorTask.repositories.SensorRepository;
import ru.smetanin.WeatherSensorTask.utils.MeasurementException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MeasurementValidationService
{
    private final SensorRepository sensorRepository;

    public void checkMeasurementBindings(BindingResult bindingResult) throws MeasurementException
    {
        if (bindingResult.hasErrors())
        {
            List<ObjectError> errors = bindingResult.getAllErrors();
            String errorMessage = errors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .reduce((acc, errMes) -> acc.concat(",\n").concat(errMes))
                    .orElse("Rest server error");
            throw new MeasurementException(errorMessage);
        }
    }

    @Transactional
    public void checkMeasurementSensor(Measurement measurement) throws MeasurementException
    {
        Sensor sensor = measurement.getSensor();
        Optional<Sensor> optSensor = sensorRepository.findByName(sensor.getName());
        optSensor.orElseThrow(() -> new MeasurementException("Such sensor was not registered!"));
    }
}
