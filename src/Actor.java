import java.util.Objects;

public class Actor {
    private final int id;
    private final String name;
    private static int currentID = 0;

    public Actor(String name) {
        this.id = currentID;
        this.name = name;
        currentID++;
    }

    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "id=" + id + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return id == actor.id && Objects.equals(name, actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
