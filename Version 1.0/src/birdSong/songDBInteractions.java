package birdSong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class songDBInteractions {
	
	public static final configuration conf = new configuration();
	
	@SuppressWarnings("static-access")
	public static final String[] security = conf.secureCreds();
	
	public static void insertSongToDB(String song, String polarity) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + security[0], "" + security[1], "" + security[2]);
		Statement statement = connection.createStatement();
		String insertQuery = "INSERT INTO SongDB (songID, songString, sentiment) VALUES( DEFAULT, \"" + song + "\"" + ", \"" + polarity + "\");";
		statement.execute(insertQuery);
		connection.close();
	}
	
	public static ResultSet pullMostRecentTweetAndSentiment() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + security[0], "" + security[1], "" + security[2]);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT Sentiment,tweet FROM tweets, analysis WHERE tweets.tweetID = analysis.tweetID ORDER BY timestamp DESC LIMIT 1");
		connection.close();
		if(result.first()){
			return result;
		} else {
			return null;
		}
	}
	
	public static List<String> retrieveSongs(ResultSet tweetAndSentiment) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + security[0], "" + security[1], "" + security[2]);
		Statement statement = connection.createStatement();
		List<String> songs = new ArrayList<String>();
		
		ResultSet result = statement.executeQuery("SELECT * FROM SongDB WHERE sentiment=\"" + tweetAndSentiment.getString("sentiment") + "\" ORDER BY songID DESC");
		while(result.next()){
			songs.add(result.getString("songString"));
		}
		connection.close();
		return songs;

	}
	public static List<String> retrieveAllSongs() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + security[0], "" + security[1], "" + security[2]);
		Statement statement = connection.createStatement();
		List<String> songs = new ArrayList<String>();
		
		ResultSet result = statement.executeQuery("SELECT * FROM SongDB ORDER BY songID DESC ");
		while(result.next()){
			songs.add(result.getString("songString"));
		}
		connection.close();
		return songs;
	}
	
	public static void deleteSongFromDB(String song) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + security[0], "" + security[1], "" + security[2]);
		Statement statement = connection.createStatement();
		String deleteQuery = "DELETE FROM SongDB WHERE songString =" + "\"" + song + "\"" ;
		statement.execute(deleteQuery);
		System.out.println("Song Deleted From Database");
		connection.close();
	}
	
}
