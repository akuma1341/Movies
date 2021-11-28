import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        DBHandler.createConnection();

        DBHandler.getGenreInfo();
        DBHandler.getActorInfo();
        DBHandler.getDirectorInfo();

        DBHandler.closeConnection();
    }
}