import java.util.Map;
import java.util.HashMap;

public class Genres {
    private static final Map<Integer, Genre> genreMap = new HashMap<>();

    public static void addGenre(Genre newGenre) {
        genreMap.put(newGenre.getID(), newGenre);
    }

    public static void showGenres() {
        StringBuilder genreNames = new StringBuilder();
        for (Map.Entry<Integer, Genre> entry: genreMap.entrySet()) {
            int currentGenreID = entry.getKey();
            String currentGenreName = entry.getValue().getName();
            if (genreNames.toString().equals("")) {
                genreNames.append(currentGenreID).append(" - ").append(currentGenreName);
            } else {
                genreNames.append("; ").append(currentGenreID).append(" - ").append(currentGenreName);
            }
        }
        System.out.println(genreNames);
    }

    public static Genre getGenreByID(int id) {
        return genreMap.get(id);
    }

    public static int getGenresCount() {
        return genreMap.size();
    }


}
