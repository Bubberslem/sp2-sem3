package dat.dtos;

import dat.entities.Exercise;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ExerciseDTO {

    private Integer id;
    private String exerciseName;
    private String primaryMuscle;
    private Exercise.Difficulty exerciseDifficulty; // enum -> string

    public ExerciseDTO(Exercise exercise) {
        this.id = exercise.getId();
        this.exerciseName = exercise.getExerciseName();
        this.primaryMuscle = exercise.getPrimaryMuscle();
        this.exerciseDifficulty = exercise.getExerciseDifficulty();
    }

    public static List<ExerciseDTO> toExerciseDTOList(List<Exercise> exercises) {
        return exercises.stream().map(ExerciseDTO::new).collect(Collectors.toList());
    }

}
