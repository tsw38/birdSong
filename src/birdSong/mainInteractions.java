package birdSong;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class mainInteractions {
	public static final songDBInteractions SDBI = new songDBInteractions();
	public static final buildMarkovSong BMS = new buildMarkovSong();
	public static final midiConversionAndCommand mCAC = new midiConversionAndCommand("temp");
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, InvalidMidiDataException, ClassNotFoundException, SQLException, InterruptedException{		
		
		/*
		 * use the below pattern to insert midi files into database
		 * Pattern angry = MidiFileManager.loadPatternFromMidi(new File("outputMusic/dest/1.mid"));
		 * String pat1 = BMS.generateCompleteSong("negative", BMS.createVoices(angry.toString(),20,2));
		 * songDBInteractions.insertSongToDB(angry.toString(), "positive");
		 * System.out.println("Inserted");
		 */
		Player player = new Player();
		
		String ants = "V1 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C5 F5 C5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C5 F5 C5 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C6 F5 C6 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C6 F5 C6 D5 Rq Rh D5 Rq Rh D5 A5 D5 A5 A5 A5 D5h D5 Rq Rh D5 Rq Rh D6 A6 D6 A6 A6 A6 D6h V0 Rw Rw Di Ei Fi Gi Ai Fi Aq G#i Ei G#q Gi Ebi Gq Di Ei Fi Gi Ai Fi Ai D6i C6i Ai Fi Ai C6h Di Ei Fi Gi Ai Fi Aq G#i Ei G#q Gi Ebi Gq Di Ei Fi Gi Ai Fi Ai D6i C6i Ai Fi Ai C6h A5i B5i C#6i D6i E6i A5i E6q F6i C#6i F6q E6i C#6i E6q A5i B5i C#6i D6i E6i C#6i E6q F6i C#6i F6q E6h A5i B5i C#6i D6i E6i A5i E6q F6i C#6i F6q E6i C#6i E6q A5i B5i C#6i D6i E6i C#6i E6q F6i C#6i F6q E6h D6i E6i F6i G6i A6i F6i A6q G#6i E6i G#6q G6i Eb6i G6q D6i E6i F6i G6i A6i F6i A6i D7i C7i A6i F6i A6i C7h D6i E6i F6i G6i A6i F6i A6q G#6i E6i G#6q G6i Eb6i G6q D6i E6i F6i G6i A6i F6i A6i D7i C7i A6i F6i A6i C7h Rq D7q+G6q+F#6q Rh Rq D7q+G6q+F#6q Rh D6i E6i F6i G6i A6i F6i A6i D7i C#7i A6i C#7i E7i D7h+F6h Rq F#7q+G7q+D8q Rh Rq F#7q+G7q+D8q Rh D7i E7i F7i G7i A7i F7i A7i D8i C#8i A7i C#8i E8i D8h+F7h";
		String two = "V0 Rh D5 A5 D5 A5 D5 Rq Rh D5 A5 F5 C5 A5 E6 D5 A5 D5 A5 A5 A5 E6 D5 A5 D5 A5 D5 A5 D5 A5 E6 A5 E6 A5 D5 A5 F5 C5 D5 A5 D5 A5 E6 A5 E6 A5 F5 C5 A5 D5 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 D5 A5 D5 A5 F5 C5 F5 C6 D5 Rq Rh D6 A6 A6 D6hD5 Rq D5 A5 E6 A5 E6 A5 D5 A5 F5 C5 F5 C5 D5 A5 E6 A5 E6 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5h D5 A5 D5 A5 F5 C6 D5 A5 D5 A5 A5 A5 E6 A5 F5 C6 F5 C6 D5 Rq Rh D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 Rq Rh D5 A5 A5 D5 A5 A5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 F5 C5 D5 A5 D5 A5 E6 A5 E6 A5 E6 A5 E6 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 E6 A5 E6 A5 D5 A5 D5 A5 D5 A5 F5 C6 D5 Rq Rh D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C6 D5 A5 A5 E6 A5 D5 A5 D5 A5 D5 A5 D5 A5 E6 A5 E6 A5 E6 A5 E6 A5 E6 A5 F5 C6 D5 Rq Rh D5 A5 D5 A5 D5 A5 D5 A5 D5 A5 F5 C6 D5 A5 F5 C5 A5 D5 A5 F5 C6 D5 Rq Rh D5 A5 D5 A5 D5 Rq Rh D5 Rq Rh D5 Rq Rh D5 A5 D5 A5 D5 A5 E6 A5 D5 A5 D5 A5 E6 D5 A5 D5 A5 E6 A5 D5 A5 D5 A5 F5 C5 F5 C6 F5 C6 F5 C6 F5 C6 D5 Rq Rh V1 h+F7h C6h Di Ei Fi Gi Ai D6i C7h D6i C7h Rq F#7q+G7q+D8q Rh D7i C7i A6i F6i C#6i F6q E6i A5i B5i C#6i E6i A5i E6i G#6q Rh D6i E6q F6i G6i A6i D7h+F6h Rq D7q+G6q+F#6q Rh Rq F#7q+G7q+D8q Rh D6i C7h D6i E6i C#6i E6q A5i B5i C#6i E6i C#8i A7i C7i A6i F6i A6i F6i Ai Fi Ai C6h A5i E6i G#6q G6i A6i F6i G6i A6i F6q E6i C#7i E7i F7i A7i F7i G7i A7i C#6i E6i F6i A6i F6q E6i C6i A6q G6i E6q A5i B5i C#6i D6i E6i F6i G6i A6i F6i A6i F6i C#6i D6i C6i Ai D6i E6i F6i G6i A6i D6i E6i C6i Ai Fi Aq G#i Ei Fi Gi Ai D6i E6i G#6i F6q E6h D6i E6i F6i A6i F6i A6i F6q E6h A5i B5i C#6i F6i A6i C#7i A6i C#7i A7i F7i A7i D8i C#6i E6i F6i A6i C#7i A6i F6i G6i Ai Fi Gi Ai Fi Ai Fi Ai C6i A6i F6i C#6i F6q E6i F6i A6i F6i A6q G#6i E6i F6i G6i A6i F6i A6q G#6q Rh D6i E6i F6q E6h A5i E6q A5i E6q F6i C#6i F6i G6i A6q G#i Ei G#q Gi Ai Fi Ai C6h Di Ei Fi Gi Ai Fi Ai C6i Ai C6h A5i B5i C#6i E6i G#6q G#i Ei Fi Gi Ai Fi Ai D6i E6i C#7i E7i F7i A7i D8h+F7h8q Rh D6i E6i F6q E6i C#6i D6i E6i A5i E6q F6i G6i A6i C7h D6i E6q A5i E6q";
		player.play(two);
		
//		List<String> songs = SDBI.retrieveAllSongs();
//		int originalSongsSize = songs.size();
//		String song = songs.get(0);
		
//		while(true){
//			
//			try{
//				List<String> newSongs = SDBI.retrieveAllSongs();
////				 mCAC.convertToMidi(new Pattern(song));
////				mCAC.convertMidiToXML();
//				
//				System.out.println("Playing: " + song.toString());
//				Player player = new Player();
//				player.play(song);
//				
//				if(newSongs.size() != originalSongsSize){
//					
//					song = newSongs.get(0);
//					originalSongsSize = newSongs.size();
//				}
//				
//			} catch(Exception e) {
//				SDBI.deleteSongFromDB(song);
//				
//				song = SDBI.retrieveAllSongs().get(0);
//				originalSongsSize = SDBI.retrieveAllSongs().size();
//			}
//		}
	}
}
