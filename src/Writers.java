import java.util.Map;
import java.util.HashMap;


public class Writers {
    private static final Map<Integer, Writer> writersMap = new HashMap<>();

    public static void addWriter(Writer newWriter) {
        writersMap.put(newWriter.getId(), newWriter);
    }

    public static Writer getWriterByID(int id) {
        return writersMap.get(id);
    }

    public static int getWritersCount() {
        return writersMap.size();
    }
}
