import java.util.List;

public class Photo
{
    public int id;
    public boolean vert;
    public List<String> tags;

    public Photo(int id, boolean vert, List<String> tags) {
        this.id = id;
        this.vert = vert;
        this.tags = tags;
    }
}