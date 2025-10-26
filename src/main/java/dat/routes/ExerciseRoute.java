package dat.routes;

import dat.controllers.ExerciseController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ExerciseRoute {

    private final ExerciseController exerciseController = new ExerciseController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", exerciseController::create);
            get("/", exerciseController::readAll);
            get("/{id}", exerciseController::read);
            put("/{id}", exerciseController::update);
            delete("/{id}", exerciseController::delete);
        };
    }
}
