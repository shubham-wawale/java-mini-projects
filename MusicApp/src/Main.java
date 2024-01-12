import model.Artist;
import model.DataSource;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if(!dataSource.open()){
            System.out.println("Could not connect to the database");
        } else {
            System.out.println("Connection established");
        }

        List<Artist> artists = dataSource.queryArtists(DataSource.ORDER_BY_DESC);
        artists.stream().forEach(s->System.out.println(s.getId() +  ": " + s.getName()));

        List<String> albums = dataSource.queryAlbumsForArtist("Iron Maiden", 1);
        albums.stream().forEach(System.out::println);

        dataSource.close();
    }
}