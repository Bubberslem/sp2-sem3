package dat.controllers;

import dat.config.HibernateConfig;
import dat.controllers.IController;

import dat.daos.ExerciseDAO;
import dat.dtos.ExerciseDTO;
import dat.entities.Exercise;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseController implements IController<ExerciseDTO, Integer> {

    private final ExerciseDAO dao;

    public ExerciseController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = ExerciseDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        ExerciseDTO exerciseDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(exerciseDTO, ExerciseDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<ExerciseDTO> exerciseDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(exerciseDTOS, ExerciseDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        ExerciseDTO jsonRequest = ctx.bodyAsClass(ExerciseDTO.class);
        // DTO
        ExerciseDTO exerciseDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(exerciseDTO, ExerciseDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        ExerciseDTO exerciseDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(exerciseDTO, Exercise.class);
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
    public ExerciseDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(ExerciseDTO.class)
                .check(e -> e.getExerciseName() != null && !e.getExerciseName().isBlank(),
                        "Exercise name must not be null or blank")
                .check(e -> e.getPrimaryMuscle() != null && !e.getPrimaryMuscle().isBlank(),
                        "Primary muscle must not be null or blank")
                .check(e -> e.getExerciseDifficulty() != null,"Exercise difficulty must not be null")
                .get();
    }
}

