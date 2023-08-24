package ru.smetanin.WeatherSensorTask.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.smetanin.WeatherSensorTask.dto.SensorDTO;
import ru.smetanin.WeatherSensorTask.services.SensorService;
import ru.smetanin.WeatherSensorTask.utils.SensorException;
import ru.smetanin.WeatherSensorTask.utils.SensorBindingException;
import ru.smetanin.WeatherSensorTask.utils.RestErrorResponse;

import javax.validation.Valid;
import java.util.List;

import static javax.swing.UIManager.get;

@RestController
@RequestMapping("/sensors")
public class SensorRestController
{
    private final SensorService sensorService;

    @Autowired
    public SensorRestController(SensorService sensorService)
    {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> sensorRegister(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) throws SensorException, SensorBindingException
    {
        sensorService.registerSensor(sensorDTO, bindingResult);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(SensorException.class)
    private ResponseEntity<RestErrorResponse> handleSensorException(SensorException e)
    {
        return new ResponseEntity<>(new RestErrorResponse("Sensor already exists!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SensorBindingException.class)
    private ResponseEntity<RestErrorResponse> handleSensorException(SensorBindingException e)
    {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMessage = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .reduce((acc, errStr) -> acc.concat(",\n").concat(errStr))
                .orElse("Rest server error...");
        RestErrorResponse response = new RestErrorResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
