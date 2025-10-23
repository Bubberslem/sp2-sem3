package dat.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class WorkoutItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Exercise exercise;

    @Column(nullable = false)
    private int sets;

    private Integer reps;          // either reps or timeSeconds should be set
    private Integer timeSeconds;

    private Integer restSeconds;
    private BigDecimal loadKg;     // optional target weight
}
