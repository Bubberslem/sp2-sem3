package dat.controllers;

import dat.config.HibernateConfig;
import dat.controllers.IController;

import dat.daos.WorkoutDAO;
import dat.dtos.WorkoutDTO;
import dat.entities.Workout;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkoutController implements IController<WorkoutDTO, Integer> {

    private final WorkoutDAO dao;

    public WorkoutController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = WorkoutDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        WorkoutDTO workoutDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(workoutDTO, WorkoutDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<WorkoutDTO> workoutDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(workoutDTOS, WorkoutDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        WorkoutDTO jsonRequest = ctx.bodyAsClass(WorkoutDTO.class);
        // DTO
        WorkoutDTO workoutDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(workoutDTO, WorkoutDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        WorkoutDTO workoutDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(workoutDTO, Workout.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public WorkoutDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(WorkoutDTO.class)
                .check( w -> w.getWorkoutTitle() != null && !w.getWorkoutTitle().isEmpty(), "Workout title must not be null or empty")
                .check( w -> w.getScheduledDate() != null, "Scheduled date must not be null")
                .check( w -> w.getWorkoutNotes() != null, "Workout notes must not be null")
                .check( w -> w.getItems() != null, "Workout items must not be null")
                .get();
    }
}

