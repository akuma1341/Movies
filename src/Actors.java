import java.util.*;

public class Actors {
    private static final Map<Integer, Actor> actorsMap = new HashMap<>();

    public static void addActor(Actor newActor) {
        actorsMap.put(newActor.getID(), newActor);
    }

    public static void showActors() {
        StringBuilder actorNames = new StringBuilder();
        for (Map.Entry<Integer, Actor> entry: actorsMap.entrySet()) {
            int currentActorID = entry.getKey();
            String currentActorName = entry.getValue().getName();
            if (actorNames.toString().equals("")) {
                actorNames.append(currentActorID).append(" - ").append(currentActorName);
            } else {
                actorNames.append("; ").append(currentActorID).append(" - ").append(currentActorName);
            }
        }
        System.out.println(actorNames);
    }

    public static int getActorsCount() {
        return actorsMap.size();
    }

    public static Actor getActorByID(int id) {
        return actorsMap.get(id);
    }
}
