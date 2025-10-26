package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final WorkoutRoute workoutRoute = new WorkoutRoute();
    private final ExerciseRoute exerciseRoute = new ExerciseRoute();
    private final WorkoutItemRoute workoutItemRoute = new WorkoutItemRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/workouts", workoutRoute.getRoutes());
            path("/exercise", exerciseRoute.getRoutes());
            path("/workout-items",workoutItemRoute.getRoutes());
        };
    }
}
