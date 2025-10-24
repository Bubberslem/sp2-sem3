package dat.dtos;

import dat.entities.Exercise;
import dat.entities.Workout;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class WorkoutDTO {

    private Integer id;
    private String workoutTitle;
    private String scheduledDate;
    private String workoutNotes;
    private List<WorkoutItemDTO> items = new ArrayList<>();

    public WorkoutDTO(Workout workout) {
        if (workout == null) return;
        this.id = workout.getId();
        this.workoutTitle = workout.getWorkoutTitle();
        LocalDate sd = workout.getScheduledDate();
        this.scheduledDate = sd != null ? sd.toString() : null;
        this.workoutNotes = workout.getWorkoutNotes();
        if (workout.getItems() != null) {
           workout.getItems().forEach(item -> items.add(new WorkoutItemDTO(item)));
        }
    }
    public static List<WorkoutDTO> toWorkoutDTOList(List<Workout> workouts) {
        return workouts.stream().map(WorkoutDTO::new).collect(Collectors.toList());
    }
}