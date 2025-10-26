package dat.dtos;

import dat.entities.Workout;
import dat.entities.WorkoutItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class WorkoutItemDTO {

    private Integer id;
    private ExerciseDTO exercise;
    private WorkoutDTO workout;
    private Integer sets;
    private Integer reps;
    private BigDecimal weight;


    public WorkoutItemDTO(WorkoutItem workoutItem) {
        if (workoutItem == null) return;
        this.id = workoutItem.getId();
        if (workoutItem.getExercise() != null) this.exercise = new ExerciseDTO(workoutItem.getExercise());
        this.sets = workoutItem.getSets();
        this.reps = workoutItem.getReps();
        this.weight = workoutItem.getWeight();
        this.workout = new WorkoutDTO(workoutItem.getWorkout());
    }
    public static List<WorkoutItemDTO> toWorkoutItemDTOList(List<WorkoutItem> workoutItems) {
        return workoutItems.stream().map(WorkoutItemDTO::new).collect(Collectors.toList());
    }
}
