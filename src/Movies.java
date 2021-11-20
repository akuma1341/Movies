import java.util.HashMap;
import java.util.Map;

public class Movies {
    private static final Map<Integer, Movie> movieMap = new HashMap<>();

    public static void addMovie(Movie newMovie) {
        movieMap.put(newMovie.getID(), newMovie);
    }

    public static Map<Integer, Movie> getMovieMap() {
        return movieMap;
    }

    public static Movie getMovieByID(int id) {
        return movieMap.get(id);
    }
}
