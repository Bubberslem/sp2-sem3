package dat.entities;

import dat.dtos.WorkoutDTO;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_name", referencedColumnName = "username", nullable = false)
    private User user;

    @Column(name = "workout_title", nullable = false)
    private String workoutTitle;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;
    @Column(name = "workout_notes")
    private String workoutNotes;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkoutItem> items = new ArrayList<>();

    public Workout(String workoutTitle, LocalDate scheduledDate, String workoutNotes) {
        this.workoutTitle = workoutTitle;
        this.scheduledDate = scheduledDate;
        this.workoutNotes = workoutNotes;
    }

    public Workout(WorkoutDTO workoutDTO){
        this.id = workoutDTO.getId();
        this.workoutTitle = workoutDTO.getWorkoutTitle();
        this.scheduledDate = LocalDate.parse(workoutDTO.getScheduledDate());
        this.workoutNotes = workoutDTO.getWorkoutNotes();
        if (workoutDTO.getItems() != null){
            workoutDTO.getItems().forEach(workoutItemDTO -> items.add(new WorkoutItem(workoutItemDTO)) );
        }
    }

    public void addItem(WorkoutItem item) {
        items.add(item);
        item.setWorkout(this);
    }
}
