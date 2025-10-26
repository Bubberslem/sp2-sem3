package dat.controllers;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.WorkoutItemDAO;
import dat.dtos.WorkoutItemDTO;
import dat.entities.WorkoutItem;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class WorkoutItemController implements IController<WorkoutItemDTO, Integer> {

    private final WorkoutItemDAO dao;

    public WorkoutItemController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = WorkoutItemDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        WorkoutItemDTO dto = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(dto, WorkoutItemDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        List<WorkoutItemDTO> dtos = dao.readAll();
        ctx.res().setStatus(200);
        ctx.json(dtos, WorkoutItemDTO.class);
    }

    @Override
    public void create(Context ctx) {
        WorkoutItemDTO jsonRequest = ctx.bodyAsClass(WorkoutItemDTO.class);
        WorkoutItemDTO created = dao.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(created, WorkoutItemDTO.class);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        WorkoutItemDTO updated = dao.update(id, validateEntity(ctx));
        ctx.res().setStatus(200);
        ctx.json(updated, WorkoutItem.class);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public WorkoutItemDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(WorkoutItemDTO.class)
                .check(wi -> wi.getExercise() != null && wi.getExercise().getId() != null,
                        "Exercise (with id) must be provided")
                .check(wi -> wi.getWorkout() != null && wi.getWorkout().getId() != null,
                        "Workout (with id) must be provided")
                .check(wi -> wi.getSets() != null && wi.getSets() >= 0,
                        "Sets must be provided and >= 0")
                .check(wi -> wi.getReps() != null && wi.getReps() >= 0,
                        "Reps must be provided and >= 0")
                .get();
    }
}
