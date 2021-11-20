import java.util.*;

public class DB {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAllMovies() {
        for (Map.Entry<Integer, Movie> entry : Movies.getMovieMap().entrySet()) {
            Movie currentMovie = entry.getValue();

            System.out.println("Название: " + currentMovie.getTitle());
            System.out.printf("Рейтинг: %.1f \n", currentMovie.getRating());
            System.out.println("Актеры: " + currentMovie.getActorsNames());
            System.out.println("Сценаристы: " + currentMovie.getWritersNames());
            System.out.println("Жанры: " + currentMovie.getGenresNames());
            System.out.println("Описание: " + currentMovie.getDescription());
            System.out.println("Режисер: " + currentMovie.getDirector());
            System.out.println("");
        }
    }

    public static void getGenreInfo() {
        System.out.println("Выберите жанр");
        Genres.showGenres();
        int idGenre = scanner.nextInt();
        Genre pickedGenre = Genres.getGenreByID(idGenre);

        int genreInMoviesCount = 0;
        double midRating = 0;
        for (Map.Entry<Integer, Movie> entry : Movies.getMovieMap().entrySet()) {
            Movie currentMovie = entry.getValue();
            for (Genre genre : currentMovie.getGenre()) {
                if (genre.getID() == pickedGenre.getID()) {
                    genreInMoviesCount++;
                    midRating += currentMovie.getRating();
                }
            }
        }
        if (midRating > 0) midRating /= genreInMoviesCount;

        System.out.println("Название: " + pickedGenre.getName());
        System.out.println("Кол-во фильмов этого жанра: " + genreInMoviesCount);
        System.out.printf("Средняя оценка фильмов данного жанра: %.1f", midRating);
        System.out.println("\n");
    }

    public static void getActorsInfo() {
        System.out.println("Выберите актера");
        Actors.showActors();
        int idActor = scanner.nextInt();
        Actor pickedActor = Actors.getActorByID(idActor);

        int moviesCount = 0;
        double bestRating = 0;
        Set<Genre> genreSet = new HashSet<>();
        for (Map.Entry<Integer, Movie> entry : Movies.getMovieMap().entrySet()) {
            Movie currentMovie = entry.getValue();
            for (Actor actor : currentMovie.getActors()) {
                if (actor.getID() == pickedActor.getID()) {
                    moviesCount++;
                    if (currentMovie.getRating() > bestRating) {
                        genreSet.clear();
                        genreSet.addAll(currentMovie.getGenre());
                        bestRating = currentMovie.getRating();
                    }
                }
            }
        }

        System.out.println("Имя актера: " + pickedActor.getName());
        System.out.println("Количество фильмов в которых снимался этот актер: " + moviesCount);
        System.out.println("Жанры с лучшим средним рейтингом фильмов:");
        System.out.println(genreSet);
        System.out.println("");
    }

    public static void getDirectorsInfo() {
        System.out.println("Выберите режиссера");
        Directors.showDirectors();
        int directorID = scanner.nextInt();
        Director pickedDirector = Directors.getDirectorByID(directorID);

        HashMap<Integer, Integer> actorInMovies = new HashMap<>();
        for (Map.Entry<Integer, Movie> entry : Movies.getMovieMap().entrySet()) {
            Movie currentMovie = entry.getValue();
            if (currentMovie.getDirector().getID() == pickedDirector.getID()) {
                for (Actor actor : currentMovie.getActors()) {
                    if (actorInMovies.containsKey(actor.getID())) {
                        int count = actorInMovies.get(actor.getID());
                        count++;
                        actorInMovies.replace(actor.getID(), count);
                    } else {
                        actorInMovies.put(actor.getID(), 1);
                    }
                }
            }
        }
        LinkedHashMap<Integer, Integer> sortedActorInMovies = sortHashMapByValues(actorInMovies);

        Map<Double, Integer> topMovies = getTopMovies(pickedDirector.getID());

        System.out.println("Имя режиссера " + pickedDirector.getName());

        System.out.println("Актеры, которые чаще всего снимались с этим режиссером:");
        showMostActors(sortedActorInMovies);

        System.out.println("Лучшие фильмы:");
        showTopMovies(topMovies);

    }

    private static void showMostActors(LinkedHashMap<Integer, Integer> sortedActorInMovies) {
        for (Map.Entry<Integer, Integer> entry: sortedActorInMovies.entrySet()) {
            int actorID = entry.getKey();
            String actorName = Actors.getActorByID(actorID).getName();
            System.out.println(actorName + " количество фильмов: " + entry.getValue());
        }
    }

    private static Map<Double, Integer> getTopMovies(int directorID) {
        Map<Double, Integer> topMovies = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Movie> entry : Movies.getMovieMap().entrySet()) {
            Movie currentMovie = entry.getValue();
            if (currentMovie.getDirector().getID() == directorID) {
                topMovies.put(currentMovie.getRating(), currentMovie.getID());
            }
        }
        return topMovies;
    }

    private static void showTopMovies(Map<Double, Integer> topMovies) {
        int topMoviesCount = 0;
        for (Map.Entry<Double, Integer> entry: topMovies.entrySet()) {
            Movie currentMovie = Movies.getMovieByID(entry.getValue());
            double currentRating = entry.getKey();
            System.out.printf("%s - рейтинг: %.1f \n", currentMovie.getTitle(), currentRating);

            topMoviesCount++;
            if (topMoviesCount == 3) break;
        }
    }

    private static LinkedHashMap<Integer, Integer> sortHashMapByValues(HashMap<Integer, Integer> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    public static void fillUpDB() {
        fillUpActors();
        fillUpWriters();
        fillUpGenres();
        fillUpDirectors();
        fillUpMovies();
    }

    private static void fillUpActors() {
        for (int i = 0; i < 5; i++) {
            String name = "Actor" + i;
            Actors.addActor(new Actor(name));
        }
    }

    private static void fillUpWriters() {
        for (int i = 0; i < 15; i++) {
            String name = "Writer" + i;
            Writers.addWriter(new Writer(name));
        }
    }

    private static void fillUpGenres() {
        for (int i = 0; i < 10; i++) {
            String name = "Genre" + i;
            Genres.addGenre(new Genre(name));
        }
    }

    private static void fillUpDirectors() {
        for (int i = 0; i < 3; i++) {
            String name = "Director" + i;
            Directors.addDirector(new Director(name));
        }
    }

    private static void fillUpMovies() {
        for (int i = 0; i < 10; i++) {
            String title = "Title" + i;
            double rating = Math.random() * 10;

            Set<Actor> actorSet = new HashSet<>();
            int actorsCount = (int) (Math.random() * 5);
            for (int j = 0; j <= actorsCount; j++) {
                int randomID = (int) (Math.random() * Actors.getActorsCount());
                actorSet.add(Actors.getActorByID(randomID));
            }

            Set<Writer> writerSet = new HashSet<>();
            int writersCount = (int) (Math.random() * 5);
            for (int j = 0; j <= writersCount; j++) {
                int randomID = (int) (Math.random() * Writers.getWritersCount());
                writerSet.add(Writers.getWriterByID(randomID));
            }

            Set<Genre> genreSet = new HashSet<>();
            int genresCount = (int) (Math.random() * 5);
            for (int j = 0; j <= genresCount; j++) {
                int randomID = (int) (Math.random() * Genres.getGenresCount());
                genreSet.add(Genres.getGenreByID(randomID));
            }

            String description = "Description" + i;

            int randomID = (int) (Math.random() * Directors.getDirectorsCount());
            Director director = Directors.getDirectorByID(randomID);

            Movies.addMovie(new Movie(title, rating, actorSet, writerSet, genreSet, description, director));
        }
    }
}
