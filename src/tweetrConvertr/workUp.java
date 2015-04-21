package tweetrConvertr;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public static configuration conf = new configuration();
	public static HashMap<Integer, String> tweetPolarity = new HashMap<Integer, String>(); //tweetID -> polarity
	public static HashMap<Integer, String> uniqueTweet = new HashMap<Integer, String>(); //tweetID -> (tweet -> polarity)
	
	public static void main(String[] args) throws Exception{
		setMaps();
		
		String tweet = "I love everything";
		String sent = "positive";
		String TempAndInst = GenerateTempoAndInstrument(tweet,sent);
		
		
//		int[] random = setChordMeasureLength(4,getBarLength(uniqueTweet.get(tweetID)));
//		
//		for(int sum : random){
//			System.out.println(sum);
//		}
		
		
		Player player = new Player();
		String sound = setBaseSongProgression(sent, tweet);
		System.out.println(sound);
		Pattern pat1 = new Pattern(sound);
		player.play(pat1);
	}
	
	public static String GeneratePolarSound(String tweet, String polarity, String TempAndInst){
		String[] chords = getV1ChordProgression(polarity, tweet);
		for(int i = 0; i < chords.length; i++){
			chords[i] = chords[i] + "w";
		}
		
		/**
		 * for now only play the chords for the progression,
		 * later take notes from each chord, make song from those notes
		 * use two voices possibly
		 * for adding to the string of music, think about either adding Markovian Chains to notes, or L-System for the notes to build a song
		 * L System per each polarity, so that there is a in depth construction of possible chord progressions depending on the song and many possible combinations
		 * 
		 */
		return TempAndInst + " " + String.join(" ", chords);
	}
	public static String setKeyType(String tweet){
		Random randomNote = new Random();
		String[] scale = {"A","B","C","D","E","F", "G"};
		String type = "";
		
		if(tweet.length()%3 == 0){
			type="b";
		}
		if(tweet.length()%3 == 0 && tweet.length()%2 == 0){
			type="#";
		}
		return scale[randomNote.nextInt(7)] + type;
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
	public static int getBarLength(String tweet){
		return tweet.length()%17;
	}
	public static String getPolarity(Integer id){
		return tweetPolarity.get(id);
	}
	public static void setMaps() throws ClassNotFoundException, SQLException{
		String[] config = configuration.secureCreds();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.254.189.198:3306/" + config[0],"" + config[1], "" + config[2]);

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT tweets.tweetID as ID, tweet, sentiment FROM tweets,analysis WHERE tweets.tweetID = analysis.tweetID ORDER BY timestamp DESC LIMIT 10");
		
		//this will link all of the tweets together so that I can use their separate parts later
		while(result.next()){
			tweetPolarity.put(result.getInt("ID"), result.getString("sentiment"));
			uniqueTweet.put(result.getInt("ID"), result.getString("tweet"));
		}
		connection.close();
	}
	public static int[] setChordMeasureLength(int chordTotal, double numOfMeasures) {
	    Random rand = new Random();
	    double randNums[] = new double[chordTotal],sum = 0;

	    for (int i = 0; i < randNums.length; i++) {
	        randNums[i] = rand.nextDouble();
	        sum += randNums[i];
	    }

	    for (int i = 0; i < randNums.length; i++) {
	        randNums[i] /= sum * numOfMeasures;
	    }
		
	    int[] randomNumbers = new int[randNums.length];
	    for(int i= 0; i < randNums.length; i++){
	    	if((int)Math.round(randNums[i]*100) == 0){
	    		randomNumbers[i] = 1;
	    	}
	    	else{
	    		randomNumbers[i] = (int)Math.round(randNums[i]*100);
	    	}
	    }
	    return randomNumbers;
	}
	public static String[] getV1ChordProgression(String polarity, String tweet){
		ChordProgression cp = null;
		Random randomNote = new Random();
		String stringString = "";
		
		switch(polarity){
		case "negative":
			String[] negProgress = {"i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(negProgress[randomNote.nextInt(4)]).setKey(setKeyType(tweet));
			for(Chord chord : cp.getChords()){
				stringString += chord + " ";
			}
			break;
		case "neutral":
			String[] neutProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V","i iv v","i IIdim V","i VIdim","i bVI iv V"};
			cp = new ChordProgression(neutProgress[randomNote.nextInt(8)]).setKey(setKeyType(tweet));
			for(Chord chord : cp.getChords()){
				stringString += chord + " ";
			}
			break;
		case "positive":
			String[] posProgress = {"I IV V","I ii V","I vi ii V","I iii vi ii V"};
			cp = new ChordProgression(posProgress[randomNote.nextInt(3)]).setKey(setKeyType(tweet));
			for(Chord chord : cp.getChords()){
				stringString += chord + "5 ";
			}
			break;
		}
		return stringString.split(" ");
	}
	public static String setBaseSongProgression(String sentiment, String tweet){
		int chordMeasureLength[] = setChordMeasureLength(getV1ChordProgression(sentiment,tweet).length, getBarLength(tweet));
		String songBase = "";
		for(String chord : getV1ChordProgression(sentiment,tweet)){
			for(int chordMeasure : chordMeasureLength){
				songBase += chord + "w ";
			}
		}
		return GenerateTempoAndInstrument(tweet, sentiment) + " " + songBase;
	}

}
