package dat.daos;

import dat.dtos.WorkoutItemDTO;
import dat.entities.WorkoutItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class WorkoutItemDAO implements IDAO<WorkoutItemDTO, Integer> {

    private static WorkoutItemDAO instance;
    private static EntityManagerFactory emf;

    public static WorkoutItemDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WorkoutItemDAO();
        }
        return instance;
    }

    @Override
    public WorkoutItemDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            WorkoutItem workoutItem = em.find(WorkoutItem.class, integer);
            return new WorkoutItemDTO(workoutItem);
        }
    }

    @Override
    public List<WorkoutItemDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<WorkoutItemDTO> query = em.createQuery("SELECT new dat.dtos.WorkoutItemDTO(w) FROM WorkoutItem w", WorkoutDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public WorkoutDTO create(WorkoutDTO workoutDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Workout workout = new Workout(workoutDTO);
            em.persist(workout);
            em.getTransaction().commit();
            return new WorkoutDTO(workout);
        }
    }

    @Override
    public WorkoutDTO update(Integer integer, WorkoutDTO workoutDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Workout w = em.find(Workout.class, integer);
            w.setWorkoutTitle(workoutDTO.getWorkoutTitle());
            w.setScheduledDate(LocalDate.parse(workoutDTO.getScheduledDate()));
            w.setWorkoutNotes(workoutDTO.getWorkoutNotes());
            Workout mergedWorkout = em.merge(w);
            em.getTransaction().commit();
            return mergedWorkout != null ? new WorkoutDTO(mergedWorkout) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Workout workout = em.find(Workout.class, integer);
            if (workout != null) {
                em.remove(workout);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Workout workout = em.find(Workout.class, integer);
            return workout != null;
        }
    }
}
