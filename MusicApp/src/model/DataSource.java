package model;
import javax.swing.table.TableCellEditor;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutionException;

public class DataSource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:/Users/HP/Desktop/Java/java-mini-projects/MusicApp/" + DB_NAME;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID= "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";
    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUMS_ID = "_id";
    public static final String COLUMN_ALBUMS_NAME = "name";
    public static final String COLUMN_ALBUMS_ARTIST = "artist";
    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST ="SELECT " +TABLE_ALBUMS + '.' + COLUMN_ALBUMS_NAME +
            " FROM " + TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "."
         + COLUMN_ALBUMS_ARTIST + " = " +TABLE_ARTISTS + "." +COLUMN_ARTISTS_ID +" WHERE "
         + TABLE_ARTISTS + "." + COLUMN_ARTISTS_NAME + " = \"";

    private Connection conn;
    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void close () {
        try {
            if(conn!=null){
                conn.close();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if(sortOrder!=ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTISTS_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder==ORDER_BY_DESC){
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        try (
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString());
        ) {
            List<Artist> artists = new ArrayList<>();
            while(results.next()){
                Artist artist = new Artist();
                artist.setId(results.getInt(COLUMN_ARTISTS_ID));
                artist.setName(results.getString(COLUMN_ARTISTS_NAME));
                artists.add(artist);
            }
            return artists;
        } catch(Exception e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder){
        String query = QUERY_ALBUMS_BY_ARTIST + artistName + "\"";
        try (
                Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(query);
        ) {
            List<String> albums = new ArrayList<>();
            while(results.next()){
                albums.add(results.getString(1));
            }
            return albums;
        } catch(Exception e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

}
