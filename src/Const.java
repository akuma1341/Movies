public class Const {
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_MOVIE_ID = "idMovie";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_MOVIE_RATING = "rating";
    public static final String COLUMN_MOVIE_DESCRIPTION = "description";
    public static final String COLUMN_MOVIE_DIRECTOR = "idDirector";

    public static final String TABLE_ACTORS = "actors";
    public static final String COLUMN_ACTORS_ID = "idActor";
    public static final String COLUMN_ACTORS_NAME = "actorName";

    public static final String TABLE_WRITERS = "writers";
    public static final String COLUMN_WRITERS_NAME = "writerName";
    public static final String COLUMN_WRITERS_ID = "idWriter";

    public static final String TABLE_GENRES = "genres";
    public static final String COLUMN_GENRES_NAME = "genreName";
    public static final String COLUMN_GENRES_ID = "idGenre";

    public static final String TABLE_DIRECTORS = "directors";
    public static final String COLUMN_DIRECTORS_NAME = "directorName";
    public static final String COLUMN_DIRECTORS_ID = "idDirector";

    public static final String TABLE_ACTORS_IN_MOVIES = "actorsinmovies";
    public static final String COLUMN_ACTOR_TO_MOVIE = "idActor";
    public static final String COLUMN_MOVIE_TO_ACTOR = "idMovie";

    public static final String TABLE_WRITERS_IN_MOVIES = "writersinmovies";
    public static final String COLUMN_WRITER_TO_MOVIE = "idWriter";
    public static final String COLUMN_MOVIE_TO_WRITER = "idMovie";

    public static final String TABLE_GENRES_IN_MOVIES = "genresinmovies";
    public static final String COLUMN_GENRE_TO_MOVIE = "idGenre";
    public static final String COLUMN_MOVIE_TO_GENRE = "idMovie";
}
