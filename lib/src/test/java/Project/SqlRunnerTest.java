package Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import Model.City;

public class SqlRunnerTest {

    public DatabaseConnector databaseConnector = new DatabaseConnector();

    @Test
    void selectOne() {
        City city = (City) databaseConnector.selectOne("command_id1", "596, \"Zaria\"", City.class);
        assertEquals(city.country_id, 69);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    void selectMany() {
        List<City> cityList = (List<City>) databaseConnector.selectMany("command_id2",
            "\"York\", 102", City.class);
        assertEquals(cityList.size(), 1);
    }

    @Test
    void update() {
        int rowsAffected = databaseConnector.update("command_id4", "\"Jaipur\", 576, 8");
        assertEquals(rowsAffected, 1);
    }

    @Test
    void insert() {
        int rowsAffected  = databaseConnector.insert(
            "command_id3", "605, \"Jaipur\", \"10\", \"2020-02-10 00:00:00\"");
        assertEquals(rowsAffected, 0);
    }

    @Test
    void delete() {
        int rowsAffected  = databaseConnector.delete("command_id5", "601, \"Jaipur\"");
        assertEquals(rowsAffected, 0);
    }
}
