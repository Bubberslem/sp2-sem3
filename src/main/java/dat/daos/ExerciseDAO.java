package dat.daos;

import dat.dtos.ExerciseDTO;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ExerciseDAO implements IDAO<ExerciseDTO, Integer> {

    private static ExerciseDAO instance;
    private static EntityManagerFactory emf;

    public static ExerciseDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ExerciseDAO();
        }
        return instance;
    }

    @Override
    public ExerciseDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Exercise exercise = em.find(Exercise.class, integer);
            return new ExerciseDTO(exercise);
        }
    }

    @Override
    public List<ExerciseDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<ExerciseDTO> query = em.createQuery("SELECT new dat.dtos.ExerciseDTO(e) FROM Exercise e", ExerciseDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public ExerciseDTO create(ExerciseDTO exerciseDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Exercise exercise = new Exercise(exerciseDTO);
            em.persist(exercise);
            em.getTransaction().commit();
            return new ExerciseDTO(exercise);
        }
    }

    @Override
    public ExerciseDTO update(Integer integer, ExerciseDTO exerciseDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Exercise e = em.find(Exercise.class, integer);
            e.setExerciseName(exerciseDTO.getExerciseName());
            e.setPrimaryMuscle(exerciseDTO.getPrimaryMuscle());
            e.setExerciseDifficulty(exerciseDTO.getExerciseDifficulty());
            Exercise mergedExercise = em.merge(e);
            em.getTransaction().commit();
            return mergedExercise != null ? new ExerciseDTO(mergedExercise) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, integer);
            if (exercise != null) {
                em.remove(exercise);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Exercise exercise = em.find(Exercise.class, integer);
            return exercise != null;
        }
    }
}
