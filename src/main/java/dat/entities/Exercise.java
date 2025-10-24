package dat.entities;

import dat.dtos.ExerciseDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "primary_muscle", nullable = false)
    private String primaryMuscle;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_difficulty", nullable = false)
    private Difficulty exerciseDifficulty;


    public Exercise(String exerciseName, String primaryMuscle, Difficulty exerciseDifficulty) {
        this.exerciseName = exerciseName;
        this.primaryMuscle = primaryMuscle;
        this.exerciseDifficulty = exerciseDifficulty;
    }

    public Exercise(ExerciseDTO exerciseDTO){
        this.id = exerciseDTO.getId();
        this.exerciseName = exerciseDTO.getExerciseName();
        this.primaryMuscle = exerciseDTO.getPrimaryMuscle();
        this.exerciseDifficulty = exerciseDTO.getExerciseDifficulty();

    }

    public enum Difficulty {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }
}

