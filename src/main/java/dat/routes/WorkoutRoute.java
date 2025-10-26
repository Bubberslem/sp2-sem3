package dat.routes;

import dat.controllers.WorkoutController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class WorkoutRoute {

    private final WorkoutController workoutController = new WorkoutController();

    protected io.javalin.apibuilder.EndpointGroup getRoutes() {
        return () -> {
            post("/", workoutController::create);
            get("/", workoutController::readAll);
            get("/{id}", workoutController::read);
            put("/{id}", workoutController::update);
            delete("/{id}", workoutController::delete);
        };
    }
}
