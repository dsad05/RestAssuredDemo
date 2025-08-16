import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.given;


public class TrelloApiFirstTest {

    private static final String API_KEY = System.getenv("TRELLO_API_KEY");
    private static final String TOKEN = System.getenv("TRELLO_API_TOKEN");
    private static final String BASE_URL = "https://api.trello.com/1";
    private static final String BOARD_ID = "YlpOEk0l";

    @Test
    public void shouldGetListsFromBoardAndAssertWithAssertJ() {
        Assertions.assertNotNull(API_KEY, "API Key is missing. Check your environment variables.");
        Assertions.assertNotNull(TOKEN, "Token is missing. Check your environment variables.");

        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/boards/" + BOARD_ID + "/lists");

        response.then()
                .statusCode(200);

        List<String> listNames = response.jsonPath().getList("name");
        List<String> listIds = response.jsonPath().getList("id");

        assertThat(listNames)
                .as("List names should be present on the board.")
                .isNotEmpty()
                .hasSize(4)
                .contains("BRAINSTORM 🤔", "TODO 📚", "DOING ⚙️", "DONE! 🙌🏽");

        assertThat(listIds)
                .as("List IDs should also be present.")
                .isNotEmpty();
    }
}