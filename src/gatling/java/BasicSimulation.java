import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BasicSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // URL de votre application
            .acceptHeader("application/json");

    private ScenarioBuilder scn = scenario("BasicScenario")
            .exec(http("Get Users")
                    .get("/users")
                    .check(status().is(200))
            )
            .exec(http("Create User")
                    .post("/users")
                    .body(StringBody("{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\" }"))
                    .check(status().is(201))
            )
            .exec(http("Delete User")
                    .delete("/users/1")
                    .check(status().is(204))
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(10))
        ).protocols(httpProtocol);
    }
}
