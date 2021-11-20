import java.util.*;

public class Movie {
    private final int id;
    private static int currentID = 0;
    private final String title;
    private final double rating;
    private final Set<Actor> actorsSet;
    private final Set<Writer> writersSet;
    private final Set<Genre> genreSet;
    private final String description;
    private final Director director;


    public Movie(String title, double rating, Set<Actor> actorsSet, Set<Writer> writersSet, Set<Genre> genreSet, String description, Director director) {
        this.id = currentID;
        currentID++;
        this.title = title;
        this.rating = rating;
        this.actorsSet = actorsSet;
        this.writersSet = writersSet;
        this.genreSet = genreSet;
        this.description = description;
        this.director = director;
    }

    public String getActorsNames() {
        StringBuilder actorNames = new StringBuilder();
        for (Actor actor: actorsSet) {
            if (actorNames.toString().equals("")) {
                actorNames.append(actor.getName());
            } else {
                actorNames.append(", ").append(actor.getName());
            }
        }
        return actorNames.toString();
    }

    public String getWritersNames() {
        StringBuilder writerNames = new StringBuilder();
        for (Writer writer: writersSet) {
            if (writerNames.toString().equals("")) {
                writerNames.append(writer.getName());
            } else {
                writerNames.append(", ").append(writer.getName());
            }
        }
        return writerNames.toString();
    }

    public String getGenresNames() {
        StringBuilder genresNames = new StringBuilder();
        for (Genre genre: genreSet) {
            if (genresNames.toString().equals("")) {
                genresNames.append(genre.getName());
            } else {
                genresNames.append(", ").append(genre.getName());
            }
        }
        return genresNames.toString();
    }

    public int getID() {
        return id;
    }

    public Set<Genre> getGenre() {
        return genreSet;
    }

    public double getRating() {
        return rating;
    }

    public Set<Actor> getActors() {
        return actorsSet;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Director getDirector() {
        return director;
    }
}
