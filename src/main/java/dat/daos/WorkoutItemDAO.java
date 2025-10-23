package dat.daos;

import dat.entities.WorkoutItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WorkoutItemDAO implements IDAO<WorkoutItem, Integer> {

    private final EntityManager em;

    public WorkoutItemDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public WorkoutItem read(Integer id) {
        return em.find(WorkoutItem.class, id);
    }

    @Override
    public List<WorkoutItem> readAll() {
        TypedQuery<WorkoutItem> q = em.createQuery("SELECT wi FROM WorkoutItem wi", WorkoutItem.class);
        return q.getResultList();
    }

    @Override
    public WorkoutItem create(WorkoutItem item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        return item;
    }

    @Override
    public WorkoutItem update(Integer id, WorkoutItem item) {
        em.getTransaction().begin();
        WorkoutItem merged = em.merge(item);
        em.getTransaction().commit();
        return merged;
    }

    @Override
    public void delete(Integer id) {
        WorkoutItem wi = em.find(WorkoutItem.class, id);
        if (wi != null) {
            em.getTransaction().begin();
            em.remove(wi);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return em.find(WorkoutItem.class, id) != null;
    }
}
