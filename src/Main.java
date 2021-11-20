public class Main {
    public static void main(String[] args) {
        DB.fillUpDB();

        DB.showAllMovies();

        DB.getGenreInfo();
        DB.getActorsInfo();
        DB.getDirectorsInfo();
    }
}