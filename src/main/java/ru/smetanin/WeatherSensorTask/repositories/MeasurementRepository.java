package ru.smetanin.WeatherSensorTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smetanin.WeatherSensorTask.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer>
{
}
