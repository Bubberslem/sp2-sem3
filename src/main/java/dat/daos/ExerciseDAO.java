package dat.daos;

import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ExerciseDAO implements IDAO<Exercise, Integer> {

    private final EntityManager em;

    public ExerciseDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public Exercise read(Integer id) {
        return em.find(Exercise.class, id);
    }

    @Override
    public List<Exercise> readAll() {
        TypedQuery<Exercise> q = em.createQuery("SELECT e FROM Exercise e", Exercise.class);
        return q.getResultList();
    }

    @Override
    public Exercise create(Exercise exercise) {
        em.getTransaction().begin();
        em.persist(exercise);
        em.getTransaction().commit();
        return exercise;
    }

    @Override
    public Exercise update(Integer id, Exercise exercise) {
        em.getTransaction().begin();
        Exercise merged = em.merge(exercise);
        em.getTransaction().commit();
        return merged;
    }

    @Override
    public void delete(Integer id) {
        Exercise e = em.find(Exercise.class, id);
        if (e != null) {
            em.getTransaction().begin();
            em.remove(e);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return em.find(Exercise.class, id) != null;
    }
}
