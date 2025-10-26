package dat.routes;

import dat.controllers.WorkoutItemController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class WorkoutItemRoute {

    private final WorkoutItemController workoutItemController = new WorkoutItemController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", workoutItemController::create);
            get("/", workoutItemController::readAll);
            get("/{id}", workoutItemController::read);
            put("/{id}", workoutItemController::update);
            delete("/{id}", workoutItemController::delete);
        };
    }
}
