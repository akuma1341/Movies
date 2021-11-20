import java.util.Map;
import java.util.HashMap;

public class Directors {
    public static Map<Integer, Director> directorsMap= new HashMap<>();

    public static void addDirector(Director newDirector) {
        directorsMap.put(newDirector.getID(), newDirector);
    }

    public static int getDirectorsCount() {
        return directorsMap.size();
    }

    public static Director getDirectorByID(int id) {
        return directorsMap.get(id);
    }

    public static void showDirectors() {
        StringBuilder directorNames = new StringBuilder();
        for (Map.Entry<Integer, Director> entry: directorsMap.entrySet()) {
            int currentDirectorID = entry.getKey();
            String currentDirectorName = entry.getValue().getName();
            if (directorNames.toString().equals("")) {
                directorNames.append(currentDirectorID).append(" - ").append(currentDirectorName);
            } else {
                directorNames.append("; ").append(currentDirectorID).append(" - ").append(currentDirectorName);
            }
        }
        System.out.println(directorNames);
    }
}
