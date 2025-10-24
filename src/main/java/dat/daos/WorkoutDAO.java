package dat.daos;

import dat.dtos.WorkoutDTO;
import dat.entities.Workout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class WorkoutDAO implements IDAO<WorkoutDTO, Integer> {

    private static WorkoutDAO instance;
    private static EntityManagerFactory emf;

    public static WorkoutDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WorkoutDAO();
        }
        return instance;
    }

    @Override
    public WorkoutDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Workout workout = em.find(Workout.class, integer);
            return new WorkoutDTO(workout);
        }
    }

    @Override
    public List<WorkoutDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<WorkoutDTO> query = em.createQuery("SELECT new dat.dtos.WorkoutDTO(w) FROM Workout w", WorkoutDTO.class);
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
