package dat.entities;

import dat.dtos.WorkoutItemDTO;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "workout_item")
public class WorkoutItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_item_id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Exercise exercise;

    @Column(name = "sets", nullable = false)
    private int sets;

    @Column(name = "reps")
    private Integer reps;          // either reps or timeSeconds should be set

    @Column(name = "weight")
    private BigDecimal weight;

    public WorkoutItem(Exercise exercise, int sets, Integer reps, BigDecimal weight) {
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public WorkoutItem(WorkoutItemDTO workoutItemDTO){
        this.id = workoutItemDTO.getId();
        this.sets = workoutItemDTO.getSets();
        this.reps = workoutItemDTO.getReps();
        this.weight = workoutItemDTO.getWeight();
    }
}
