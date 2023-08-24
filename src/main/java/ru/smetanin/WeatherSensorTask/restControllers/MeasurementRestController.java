package ru.smetanin.WeatherSensorTask.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.smetanin.WeatherSensorTask.dto.MeasurementDTO;
import ru.smetanin.WeatherSensorTask.services.MeasurementService;
import ru.smetanin.WeatherSensorTask.utils.RestErrorResponse;
import ru.smetanin.WeatherSensorTask.utils.MeasurementException;
import ru.smetanin.WeatherSensorTask.utils.SensorException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementRestController
{
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementRestController(MeasurementService measurementService)
    {
        this.measurementService = measurementService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                               BindingResult bindingResult) throws MeasurementException, SensorException
    {
        measurementService.addMeasurement(measurementDTO, bindingResult);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements()
    {
        return measurementService.getAllMeasurement();
    }

    @GetMapping("rainyDaysCount")
    public Long getRainyDaysCount()
    {
        return measurementService.getRainyDaysCount();
    }

    @ExceptionHandler(MeasurementException.class)
    private ResponseEntity<RestErrorResponse> handleMeasurementException(MeasurementException measurementException)
    {
        RestErrorResponse response = new RestErrorResponse(measurementException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
