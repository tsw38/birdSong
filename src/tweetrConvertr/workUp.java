package tweetrConvertr;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;



public class workUp {

	public static void main(String[] args) throws Exception{
		configuration conf = new configuration();
		String[] config = configuration.secureCreds();
		HashMap<Integer, Integer> lengthMap = new HashMap<Integer, Integer>(); //tweetID -> Length
		HashMap<String, String> tweetPolarity; //tweet -> polarity
		HashMap<Integer, HashMap<String, String>> uniqueTweet = new HashMap<Integer, HashMap<String, String>>(); //tweetID -> (tweet -> polarity)
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + config[0],"" + config[1], "" + config[2]);

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT tweets.tweetID as ID, tweet, LENGTH(tweet)%17 AS length,sentiment FROM tweets,analysis WHERE tweets.tweetID = analysis.tweetID ORDER BY timestamp DESC LIMIT 5");
			
			//this will link all of the tweets together so that I can use their separate parts later
			while(result.next()){
				tweetPolarity = new HashMap<String, String>();
	
				lengthMap.put(result.getInt("ID"), result.getInt("length"));
				tweetPolarity.put(result.getString("tweet"), result.getString("sentiment"));
				uniqueTweet.put(result.getInt("ID"), tweetPolarity);
			}
			
			
			
			//for now I dont need the database anymore so this is where the generator comes in
			connection.close();

		    Player player = new Player();
		    String tweet = "Could Mad Men all be a book that Ken Cosgrove has written in the future #MadMen";
		    String sent = "negative";
		    
		    String sound = GeneratePolarSound(tweet,sent, GenerateTempoAndInstrument(tweet,sent));
		    Pattern pat1 = new Pattern(GeneratePolarSound(tweet,sent, GenerateTempoAndInstrument(tweet,sent)));
		    System.out.println(sound);
		    player.play(sound);

		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Note[]> getNotesForChord(ChordProgression cp){
		ArrayList<Note[]> chordProgressionList= new ArrayList<Note[]>();
		for(Chord chord : cp.getChords()){
			Note[] chordNotes = chord.getNotes();
			chordProgressionList.add(chordNotes);
		}
		return chordProgressionList;
	}
	
	public static String GeneratePolarSound(String tweet, String polarity, String TempAndInst){
		String stringString = "";
		Random randomNote = new Random();
		ChordProgression cp;
		String[] scale = {"A","B","C","D","E","F", "G"};
		String scoreKey = scale[randomNote.nextInt(7)] + setKeyType(tweet);
		char[] duration = {'w','h','Q'};
		
		/**
		 * for now only play the chords for the progression,
		 * later take notes from each chord, make song from those notes
		 * use two voices possibly
		 * for adding to the string of music, think about either adding Markovian Chains to notes, or L-System for the notes to build a song
		 * L System per each polarity, so that there is a in depth construction of possible chord progressions depending on the song and many possible combinations
		 * 
		 */
		
		switch(polarity){
		case "negative":
			String[] negProgress = {"i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(negProgress[randomNote.nextInt(4)]).setKey(scoreKey);

			Iterator<Note[]> itr = getNotesForChord(cp).iterator();
			while(itr.hasNext()){
				Note[] notes = itr.next();
				for(Note note : notes){
					System.out.println(note);
				}
				System.out.println();
			}
			
			for(Chord chord : cp.getChords()){
				stringString += chord + "" + duration[randomNote.nextInt(3)] + " ";;
			}
			break;
			
		case "neutral":
			String[] neutProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V","i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(neutProgress[randomNote.nextInt(8)]).setKey(scoreKey);
			for(Chord chord : cp.getChords()){
				stringString += chord + "" + duration[randomNote.nextInt(3)] + " ";;
			}
			break;
			
		case "positive":
			String[] posProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V"};
			cp = new ChordProgression(posProgress[randomNote.nextInt(3)]).setKey(scoreKey);
			for(Chord chord : cp.getChords()){
				stringString += chord + "5" + duration[randomNote.nextInt(3)] + " ";
			}
			break;
		}
		return TempAndInst + " " + stringString;
	}
	
	public static String setKeyType(String tweet){
		String type = "";
		if(tweet.length()%3 == 0){
			type="b";
		}
		if(tweet.length()%3 == 0 && tweet.length()%2 == 0){
			type="#";
		}
		return type;
	}
	
	public static String GenerateTempoAndInstrument(String tweet, String polarity){
		Random init = new Random();
		String instrument = "";
		int low = 0;
		int high = 0;
		int rand = 0;
		
		switch(polarity){
		case "negative":
			low = 60; high = 100;
			rand = init.nextInt(high - low) + low;
			String[] negInstruments ={"Church_Organ","Bassoon", "Baritone_Sax", "Brass_Section", "French_Horn", "Contrabass", "Synth_Bass_1"};
			instrument = negInstruments[init.nextInt(negInstruments.length)];
			break;
		case "neutral":
			low = 24; high = 80;
			rand = init.nextInt(high - low) + low;
			String[] neuInstruments ={"Piano", "Vibraphone", "Alto_Sax", "Violin", "Xylophone", "Harmonica", "French_Horn", "Pan_Flute", "String_Ensamble", "Blown_Bottle", "Ocarina", "Steel_Drums"};
			instrument = neuInstruments[init.nextInt(neuInstruments.length)];
			break;
		case "positive":
			low = 100; high = 256;
			rand = init.nextInt(high - low) + low;
			String[] posInstruments ={"Piano", "Flute", "Clarinet", "Oboe", "Soprano_Sax", "Glockenspiel", "Tinkle_Bell", "Piccolo", "Tubular_Bells", "Recorder", "Whistle", "Bird_Tweet"};
			instrument = posInstruments[init.nextInt(posInstruments.length)];
			break;
		}
		
		return "T" + rand + " I[" + instrument + "]";
	}


}
