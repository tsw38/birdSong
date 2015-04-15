package tweetrConvertr;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;

import org.jfugue.player.Player;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;



public class workUp {

	public static void main(String[] args) throws Exception{
		HashMap<String, Double> polarity = new HashMap<String, Double>();
		//try connecting to the database
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/tsw38_oauth","tsw38_admin","lucky#19");

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT Sentiment, ROUND((COUNT(*)/(SELECT COUNT(*) FROM analysis))*100,2) AS Polarity FROM analysis GROUP BY Sentiment ORDER BY Sentiment ASC");
			
			//create a Mapping of Polarity to how strongly people feel about it
			
			//Put the percentages into the map
			while(result.next()){
				polarity.put(result.getString("Sentiment"), Double.parseDouble(result.getString("Polarity")));
			}
			//for now I dont need the database anymore so this is where the generator comes in
			connection.close();
//			
//			//Idea for now, just take the polarity and decide if positive or negative sounds should come out
//			ChordProgression chordProgressions;
//			if(polarity.get("negative") >= 50.00){
//				System.out.println("this is a bad feeling");
//				Player player = new Player();
//				String playthis = " D E F G A  C C E F G B D E F  D E F C D E F G A D E F G A ";
//				player.play(playthis);
//			}
//			else if(polarity.get("neutral") >= 30.00){
//				Player player = new Player();
//				player.play("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq   V1 I[Flute] Rw | Rw | GmajQQQ CmajQ");
//				
//			}
//			else if(polarity.get("positive") >= 50.00){
//				Player player = new Player();
//				chordProgressions = new ChordProgression("I IV V ii");
//				player.play(chordProgressions);
//			}
					   		    
//		    for (Chord chord : chordsArray) {
//		      System.out.print("Chord "+chord+" has these notes: ");
//		      
//		      Note[] notes = chord.getNotes();
//		      for (Note note : notes) {
//		        System.out.print(note+" ");
//		      }
//		      System.out.println();
//		    }
//
		    Player player = new Player();
		    
		    String tweet = "I he the world bcuse of so many stupid things";
		    String sent = "negative";
		    String sound = GeneratePolarSound(tweet,sent, GenerateTempoAndInstrument(tweet,sent));
		    
		    //System.out.println(sound);
		    // player.play(sound);
		    
		    
//		    Chord[] chords = cp.setKey("C").getChords();
//		    for(Chord chord : chords){
//		    	System.out.println("Chord " + chord + " has these notes: ");
//		    	Note[] notes = chord.getNotes();
//		    	System.out.println();
//		    }
//		    player.play(cp);
			
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static String GeneratePolarSound(String tweet, String polarity, String TempAndInst){
		String stringString = "";
		Random randomNote = new Random();
		ChordProgression cp;
		
		String[] scale = {"A","B","C","D","E","F", "G"};
		String scoreKey = scale[randomNote.nextInt(7)] + setKeyType(tweet);
		
		//duration is for the length of the notes, but for now I am only dealing with chords
		char[] duration = {'w','h','Q'};
		//for adding to the string of music, think about either adding Markovian Chains to notes, or L-System for the notes to build a song
		//L System per each polarity, so that there is a in depth construction of possible chord progressions depending on the song and many possible combinations
		int tweetLength = randomNote.nextInt(tweet.length());
		
		switch(polarity){
		case "negative":
			String[] negProgress = {"i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(negProgress[randomNote.nextInt(4)]).setKey(scoreKey);
			
			while(stringString.length() < tweetLength){
				stringString += scale[randomNote.nextInt(7)] + ""+ (randomNote.nextInt(5)+1) + "" + duration[randomNote.nextInt(3)] + " ";
			}
			break;
			
		case "neutral":
			String[] neutProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V","i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(neutProgress[randomNote.nextInt(8)]).setKey(scoreKey);
			while(stringString.length() < tweetLength){
				stringString += scale[randomNote.nextInt(7)] + "" + duration[randomNote.nextInt(3)] + " ";
			}
			break;
			
		case "positive":
			String[] posProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V"};
			cp = new ChordProgression(posProgress[randomNote.nextInt(4)]).setKey(scoreKey);
			while(stringString.length() < tweetLength){
				stringString += scale[randomNote.nextInt(7)] + "" + (randomNote.nextInt(4)+3) + "" + duration[randomNote.nextInt(3)] + " ";
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
			String[] negInstruments ={"Church_Organ","Bassoon", "Baritone_Sax", "Brass_Section", "French_Horn", "Timpini", "Contrabass", "Synth_Bass_1"};
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
			String[] posInstruments ={"Piano", "Flute", "Clarinet", "Oboe", "Soprano_Sax", "Glockenspiel", "Tinkle_Bell", "Piccolo", "Harpsichord", "Tubular_Bells", "Recorder", "Whistle", "Bird_Tweet"};
			instrument = posInstruments[init.nextInt(posInstruments.length)];
			break;
		}
		
		return "T" + rand + " I[" + instrument + "]";
	}


}
