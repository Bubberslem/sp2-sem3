package dat.entities;

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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_name", referencedColumnName = "username", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private LocalDate scheduledDate;
    private String notes;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkoutItem> items = new ArrayList<>();

    public void addItem(WorkoutItem item) {
        items.add(item);
        item.setWorkout(this);
    }
}
