package ru.smetanin.WeatherSensorTask.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="sensor")
public class Sensor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Length(min = 3, max = 30, message = "Sensor name is not valid!")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurementList;
}
