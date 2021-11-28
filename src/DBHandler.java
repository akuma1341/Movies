import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class DBHandler extends DBConfig {
    private static Connection connection;
    private static final Scanner scanner = new Scanner(System.in);

    public static int addActor(Actor actor) throws SQLException {
        String sqlCommand = String.format("INSERT INTO %s (%s) VALUES ('%s')",
                Const.TABLE_ACTORS, Const.COLUMN_ACTORS_NAME, actor.getName());
        return insertInto(sqlCommand);
    }

    public static int addWriter(Writer writer) throws SQLException {
        String sqlCommand = String.format("INSERT INTO %s (%s) VALUES ('%s')",
                Const.TABLE_WRITERS, Const.COLUMN_WRITERS_NAME, writer.getName());
        return insertInto(sqlCommand);
    }

    public static int addGenre(Genre genre) throws SQLException {
        String sqlCommand = String.format("INSERT INTO %s (%s) VALUES ('%s')",
                Const.TABLE_GENRES, Const.COLUMN_GENRES_NAME, genre.getName());
        return insertInto(sqlCommand);
    }

    public static int addDirector(Director director) throws SQLException {
        String sqlCommand = String.format("INSERT INTO %s (%s) VALUES ('%s')",
                Const.TABLE_DIRECTORS, Const.COLUMN_DIRECTORS_NAME, director.getName());
        System.out.println(sqlCommand);
        return insertInto(sqlCommand);
    }

    public static int addMovie(Movie movie) throws SQLException {
        String sqlCommand = String.format(Locale.US, "INSERT INTO %s " +
                        "(%s,%s,%s,%s) " +
                        "VALUES ('%s',%f,'%s',%d)",
                Const.TABLE_MOVIES,
                Const.COLUMN_MOVIE_TITLE, Const.COLUMN_MOVIE_RATING, Const.COLUMN_MOVIE_DESCRIPTION, Const.COLUMN_MOVIE_DIRECTOR,
                movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getDirector().getID());
        System.out.println(sqlCommand);
        int createdMovieID = insertInto(sqlCommand);

        String format = "INSERT INTO %s " +
                "(%s,%s) " +
                "VALUES(%d,%d)";

        for (Actor actor : movie.getActorsSet()) {
            sqlCommand = String.format(format,
                    Const.TABLE_ACTORS_IN_MOVIES,
                    Const.COLUMN_ACTOR_TO_MOVIE, Const.COLUMN_MOVIE_TO_ACTOR,
                    actor.getID(), createdMovieID);
            insertInto(sqlCommand);
        }

        for (Writer writer : movie.getWritersSet()) {
            sqlCommand = String.format(format,
                    Const.TABLE_WRITERS_IN_MOVIES,
                    Const.COLUMN_WRITER_TO_MOVIE, Const.COLUMN_MOVIE_TO_WRITER,
                    writer.getID(), createdMovieID);
            insertInto(sqlCommand);
        }

        for (Genre genre : movie.getGenresSet()) {
            sqlCommand = String.format(format,
                    Const.TABLE_GENRES_IN_MOVIES,
                    Const.COLUMN_GENRE_TO_MOVIE, Const.COLUMN_MOVIE_TO_GENRE,
                    genre.getID(), createdMovieID);
            insertInto(sqlCommand);
        }

        return createdMovieID;
    }

    public static Actor getActorByID(int id) throws SQLException {
        Actor actor = null;
        String sqlCommand = String.format("SELECT * FROM %s WHERE %s = %d",
                Const.TABLE_ACTORS, Const.COLUMN_ACTORS_ID, id);
        ResultSet resultSet = selectData(sqlCommand);
        if (resultSet.first()) {
            int actorID = resultSet.getInt(Const.COLUMN_ACTORS_ID);
            String actorName = resultSet.getString(Const.COLUMN_ACTORS_NAME);
            actor = new Actor(actorID, actorName);
        }
        return actor;
    }

    public static Writer getWriterByID(int id) throws SQLException {
        Writer writer = null;
        String sqlCommand = String.format("SELECT * FROM %s WHERE %s = %d",
                Const.TABLE_WRITERS, Const.COLUMN_WRITERS_ID, id);
        ResultSet resultSet = selectData(sqlCommand);
        if (resultSet.first()) {
            int writerID = resultSet.getInt(Const.COLUMN_WRITERS_ID);
            String writerName = resultSet.getString(Const.COLUMN_WRITERS_NAME);
            writer = new Writer(writerID, writerName);
        }
        return writer;
    }

    public static Genre getGenreByID(int id) throws SQLException {
        Genre genre = null;
        String sqlCommand = String.format("SELECT * FROM %s WHERE %s = %d",
                Const.TABLE_GENRES, Const.COLUMN_GENRES_ID, id);
        ResultSet resultSet = selectData(sqlCommand);
        if (resultSet.first()) {
            int genreID = resultSet.getInt(Const.COLUMN_GENRES_ID);
            String genreName = resultSet.getString(Const.COLUMN_GENRES_NAME);
            genre = new Genre(genreID, genreName);
        }
        return genre;
    }

    public static Director getDirectorByID(int id) throws SQLException {
        Director director = null;
        String sqlCommand = String.format("SELECT * FROM %s WHERE %s = %d",
                Const.TABLE_DIRECTORS, Const.COLUMN_DIRECTORS_ID, id);
        ResultSet resultSet = selectData(sqlCommand);
        if (resultSet.first()) {
            int directorID = resultSet.getInt(Const.COLUMN_DIRECTORS_ID);
            String directorName = resultSet.getString(Const.COLUMN_DIRECTORS_NAME);
            director = new Director(directorID, directorName);
        }
        return director;
    }

    private static ResultSet selectData(String sqlCommand) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sqlCommand);
    }

    public static void createConnection() throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    private static int insertInto(String sqlCommand) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlCommand);

        ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() AS id");

        resultSet.first();
        return resultSet.getInt("id");
    }

    public static void getGenreInfo() throws SQLException {
        int selectedID = selectGenreID();
        showSelectedGenreInfo(selectedID);
    }

    private static int selectGenreID() throws SQLException {
        String sqlCommand = "SELECT * FROM " + Const.TABLE_GENRES;
        ResultSet resultSet = selectData(sqlCommand);
        System.out.println("Выберите жанр:");
        int delimiter = 0;
        while (resultSet.next()) {
            int genreID = resultSet.getInt(Const.COLUMN_GENRES_ID);
            String genreName = resultSet.getString(Const.COLUMN_GENRES_NAME);
            System.out.printf("%d- %s; ", genreID, genreName);
            delimiter++;
            if (delimiter == 5) {
                delimiter = 0;
                System.out.println("");
            }
        }

        return scanner.nextInt();
    }

    private static void showSelectedGenreInfo(int selectedID) throws SQLException {
        String sqlCommand;
        sqlCommand = String.format("SELECT %s.%s, AVG(%s) AS rating, COUNT(%s) AS count " +
                        "FROM %s,%s " +
                        "WHERE %s.%s = %d " +
                        "AND %s in" +
                        "(SELECT %s FROM %s WHERE %s = %d)",
                Const.TABLE_GENRES, Const.COLUMN_GENRES_NAME, Const.COLUMN_MOVIE_RATING, Const.COLUMN_MOVIE_ID,
                Const.TABLE_MOVIES, Const.TABLE_GENRES,
                Const.TABLE_GENRES, Const.COLUMN_GENRES_ID, selectedID,
                Const.COLUMN_MOVIE_ID,
                Const.COLUMN_MOVIE_ID, Const.TABLE_GENRES_IN_MOVIES, Const.COLUMN_GENRES_ID, selectedID);

        ResultSet resultSet = selectData(sqlCommand);
        resultSet.first();
        System.out.println("Название: " + resultSet.getString(Const.COLUMN_GENRES_NAME));
        System.out.println("Количество фильмов этого жанра: " + resultSet.getInt("count"));
        System.out.printf("Средний рейтинг фильмов этого жанра: %.1f \n", resultSet.getDouble("rating"));
    }

    public static void getActorInfo() throws SQLException {
        int selectedID = selectActorID();
        showSelectedActorInfo(selectedID);
    }

    private static int selectActorID() throws SQLException {
        String sqlCommand = "SELECT * FROM " + Const.TABLE_ACTORS;
        ResultSet resultSet = selectData(sqlCommand);
        System.out.println("Выберите актера:");
        int delimiter = 0;
        while (resultSet.next()) {
            int actorID = resultSet.getInt(Const.COLUMN_ACTORS_ID);
            String actorName = resultSet.getString(Const.COLUMN_ACTORS_NAME);
            System.out.printf("%d- %s; ", actorID, actorName);
            delimiter++;
            if (delimiter == 5) {
                delimiter = 0;
                System.out.println("");
            }
        }

        return scanner.nextInt();
    }

    private static void showSelectedActorInfo(int id) throws SQLException {
        String sqlCommand;
        sqlCommand = String.format("SELECT %s.%s, COUNT(%s.%s) AS count " +
                        "FROM %s, %s " +
                        "WHERE %s.%s = %d " +
                        "AND %s.%s = %d",
                Const.TABLE_ACTORS, Const.COLUMN_ACTORS_NAME, Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_ACTOR_TO_MOVIE,
                Const.TABLE_ACTORS, Const.TABLE_ACTORS_IN_MOVIES,
                Const.TABLE_ACTORS, Const.COLUMN_ACTORS_ID, id,
                Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_ACTOR_TO_MOVIE, id);
        ResultSet resultSet = selectData(sqlCommand);
        resultSet.first();
        String actorName = resultSet.getString(Const.COLUMN_ACTORS_NAME);
        int moviesCount = resultSet.getInt("count");

        sqlCommand = String.format("SELECT %s FROM %s WHERE %s in" +
                        "(SELECT %s FROM %s WHERE %s =" +
                        "(SELECT %s.%s FROM %s " +
                        "JOIN %s " +
                        "ON %s.%s = %s.%s " +
                        "WHERE %s.%s = %d " +
                        "AND %s =" +
                        "(SELECT MAX(%s) FROM %s WHERE %s in" +
                        "(SELECT %s FROM %s WHERE %s = %d))))",
                Const.COLUMN_GENRES_NAME, Const.TABLE_GENRES, Const.COLUMN_GENRES_ID,
                Const.COLUMN_GENRE_TO_MOVIE, Const.TABLE_GENRES_IN_MOVIES, Const.COLUMN_MOVIE_TO_GENRE,
                Const.TABLE_MOVIES, Const.COLUMN_MOVIE_ID, Const.TABLE_MOVIES,
                Const.TABLE_ACTORS_IN_MOVIES,
                Const.TABLE_MOVIES, Const.COLUMN_MOVIE_ID, Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_MOVIE_TO_ACTOR,
                Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_ACTOR_TO_MOVIE, id,
                Const.COLUMN_MOVIE_RATING,
                Const.COLUMN_MOVIE_RATING, Const.TABLE_MOVIES, Const.COLUMN_MOVIE_ID,
                Const.COLUMN_MOVIE_TO_ACTOR, Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_ACTOR_TO_MOVIE, id);
        resultSet = selectData(sqlCommand);
        String genresWithMoviesBestRating = "";
        while (resultSet.next()) {
            if (genresWithMoviesBestRating.equals("")) {
                genresWithMoviesBestRating = resultSet.getString(Const.COLUMN_GENRES_NAME);
            } else {
                genresWithMoviesBestRating += ", " + resultSet.getString(Const.COLUMN_GENRES_NAME);
            }
        }

        System.out.println("Имя актера: " + actorName);
        System.out.println("Количество фильмов актера: " + moviesCount);
        System.out.println("Жанры с лучшим средним рейтингом фильмов:");
        System.out.println(genresWithMoviesBestRating);
    }

    public static void getDirectorInfo() throws SQLException {
        int selectedID = selectDirectorID();
        showSelectedDirectorInfo(selectedID);
    }

    private static int selectDirectorID() throws SQLException {
        String sqlCommand = "SELECT * FROM " + Const.TABLE_DIRECTORS;
        ResultSet resultSet = selectData(sqlCommand);
        System.out.println("Выберите режисера:");
        int delimiter = 0;
        while (resultSet.next()) {
            int directorID = resultSet.getInt(Const.COLUMN_DIRECTORS_ID);
            String actorName = resultSet.getString(Const.COLUMN_DIRECTORS_NAME);
            System.out.printf("%d- %s; ", directorID, actorName);
            delimiter++;
            if (delimiter == 5) {
                delimiter = 0;
                System.out.println("");
            }
        }

        return scanner.nextInt();
    }

    private static void showSelectedDirectorInfo(int id) throws SQLException {
        String directorName = getDirectorByID(id).getName();

        String sqlCommand = String.format("SELECT %s, COUNT(%s) AS count FROM" +
                        "(SELECT %s FROM %s JOIN %s " +
                        "ON %s.%s = %s.%s " +
                        "WHERE %s.%s in" +
                        "(SELECT %s FROM %s WHERE %s = %s)) AS actorNames " +
                        "GROUP BY %s " +
                        "ORDER BY count DESC LIMIT 3",
                Const.COLUMN_ACTORS_NAME, Const.COLUMN_ACTORS_NAME,
                Const.COLUMN_ACTORS_NAME, Const.TABLE_ACTORS, Const.TABLE_ACTORS_IN_MOVIES,
                Const.TABLE_ACTORS, Const.COLUMN_ACTORS_ID, Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_ACTOR_TO_MOVIE,
                Const.TABLE_ACTORS_IN_MOVIES, Const.COLUMN_MOVIE_TO_ACTOR,
                Const.COLUMN_MOVIE_ID, Const.TABLE_MOVIES, Const.COLUMN_MOVIE_DIRECTOR, id,
                Const.COLUMN_ACTORS_NAME);
        ResultSet resultSet = selectData(sqlCommand);
        String actorsCount = "";
        while (resultSet.next()) {
            String actorName = resultSet.getString(Const.COLUMN_ACTORS_NAME);
            int actorCount = resultSet.getInt("count");
            if (actorsCount.equals("")) {
                actorsCount += actorName + ": " + actorCount;
            } else {
                actorsCount += "\n" + actorName + ": " + actorCount;
            }
        }

        sqlCommand = String.format("SELECT %s, %s " +
                        "FROM %s JOIN %s " +
                        "ON %s.%s = %s.%s " +
                        "WHERE %s.%s = %d " +
                        "ORDER BY %s DESC " +
                        "LIMIT 3",
                Const.COLUMN_MOVIE_TITLE, Const.COLUMN_MOVIE_RATING,
                Const.TABLE_MOVIES, Const.TABLE_DIRECTORS,
                Const.TABLE_MOVIES, Const.COLUMN_MOVIE_DIRECTOR, Const.TABLE_DIRECTORS, Const.COLUMN_DIRECTORS_ID,
                Const.TABLE_MOVIES, Const.COLUMN_MOVIE_DIRECTOR, id,
                Const.COLUMN_MOVIE_RATING);
        resultSet = selectData(sqlCommand);
        String directorsMoviesTop = "";
        while (resultSet.next()) {
            String movieTitle = resultSet.getString(Const.COLUMN_MOVIE_TITLE);
            String movieRating = String.format("%.1f", resultSet.getDouble(Const.COLUMN_MOVIE_RATING));
            if (directorsMoviesTop.equals("")) {
                directorsMoviesTop += movieTitle + ": рейтинг " + movieRating;
            } else {
                directorsMoviesTop += "\n" + movieTitle + ": рейтинг " + movieRating;
            }
        }

        System.out.println("Имя режиссера: " + directorName);
        System.out.println("Актеры, которые чаще всего снимались в фильмах:");
        System.out.println(actorsCount);
        System.out.println("\nТоп фильмов:");
        System.out.println(directorsMoviesTop);
    }
}
