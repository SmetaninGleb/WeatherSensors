package ru.smetanin.WeatherSensorTask.models;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "measurement")
public class Measurement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_raining")
    @NotNull
    private Boolean raining;

    @Column(name = "temperature")
    @Min(value = -100, message = "Temperature range should be from -100 to 100!")
    @Max(value = 100, message = "Temperature range should be from -100 to 100!")
    @NotNull
    private Float value;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;
}
