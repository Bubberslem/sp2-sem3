package dat.daos;

import dat.dtos.WorkoutItemDTO;
import dat.entities.WorkoutItem;
import dat.entities.Workout;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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
            TypedQuery<WorkoutItemDTO> query = em.createQuery(
                "SELECT new dat.dtos.WorkoutItemDTO(w) FROM WorkoutItem w",
                WorkoutItemDTO.class
            );
            return query.getResultList();
        }
    }

    @Override
    public WorkoutItemDTO create(WorkoutItemDTO workoutItemDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WorkoutItem workoutItem = new WorkoutItem();
            // scalar fields
            workoutItem.setSets(workoutItemDTO.getSets() != null ? workoutItemDTO.getSets() : 0);
            workoutItem.setReps(workoutItemDTO.getReps());
            workoutItem.setWeight(workoutItemDTO.getWeight());
            // resolve exercise relation if DTO provides an exercise id
            if (workoutItemDTO.getExercise() != null && workoutItemDTO.getExercise().getId() != null) {
                Exercise exercise = em.find(Exercise.class, workoutItemDTO.getExercise().getId());
                workoutItem.setExercise(exercise);
            }
            // resolve workout relation if DTO provides a workout id
            if (workoutItemDTO.getWorkout() != null && workoutItemDTO.getWorkout().getId() != null) {
                Workout workout = em.find(Workout.class, workoutItemDTO.getWorkout().getId());
                workoutItem.setWorkout(workout);
            }
            em.persist(workoutItem);
            em.getTransaction().commit();
            return new WorkoutItemDTO(workoutItem);
        }
    }

    @Override
    public WorkoutItemDTO update(Integer integer, WorkoutItemDTO workoutItemDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WorkoutItem wi = em.find(WorkoutItem.class, integer);
            if (wi == null) {
                em.getTransaction().commit();
                return null;
            }
            // update scalar fields if provided
            if (workoutItemDTO.getSets() != null) wi.setSets(workoutItemDTO.getSets());
            if (workoutItemDTO.getReps() != null) wi.setReps(workoutItemDTO.getReps());
            if (workoutItemDTO.getWeight() != null) wi.setWeight(workoutItemDTO.getWeight());
            // update exercise relation if DTO contains an exercise id
            if (workoutItemDTO.getExercise() != null && workoutItemDTO.getExercise().getId() != null) {
                Exercise exercise = em.find(Exercise.class, workoutItemDTO.getExercise().getId());
                wi.setExercise(exercise);
            }
            // update workout relation if DTO contains a workout id
            if (workoutItemDTO.getWorkout() != null && workoutItemDTO.getWorkout().getId() != null) {
                Workout workout = em.find(Workout.class, workoutItemDTO.getWorkout().getId());
                wi.setWorkout(workout);
            }
            WorkoutItem merged = em.merge(wi);
            em.getTransaction().commit();
            return merged != null ? new WorkoutItemDTO(merged) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WorkoutItem wi = em.find(WorkoutItem.class, integer);
            if (wi != null) {
                em.remove(wi);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            WorkoutItem wi = em.find(WorkoutItem.class, integer);
            return wi != null;
        }
    }
}
