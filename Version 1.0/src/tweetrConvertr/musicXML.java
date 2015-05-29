package tweetrConvertr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.xml.parsers.ParserConfigurationException;

import org.jfugue.devices.MidiParserReceiver;
import org.jfugue.integration.LilyPondParserListener;
import org.jfugue.integration.MusicXmlParserListener;
import org.jfugue.integration.MusicXmlParser_R;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParser;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.parser.Parser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;

public class musicXML {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InvalidMidiDataException, IOException, MidiUnavailableException, ParserConfigurationException {
		File inputFile = new File("outputMusic/frenchSong.midi");
		Player player = new Player();
		Pattern song = new Pattern("V0 C5q D5q E5q C5q C5q D5q E5q C5q E5q F5q G5h E5q F5q G5h G5i A5i G5i F5i E5q C5q G5i A5i G5i F5i E5q C5q C5q G4q C5h C5q G4q C5h V1 Rw2 C5q D5q E5q C5q C5q D5q E5q C5q E5q F5q G5h E5q F5q G5h G5i A5i G5i F5i E5q C5q G5i A5i G5i F5i E5q C5q C5q G4q C5h C5q G4q C5h V3 Rw4 C5q D5q E5q C5q C5q D5q E5q C5q E5q F5q G5h E5q F5q G5h G5i A5i G5i F5i E5q C5q G5i A5i G5i F5i E5q C5q C5q G4q C5h C5q G4q C5h");
		
		
		
		FileWriter outputConversion = new FileWriter("something.xml");
		
		
		
		
		
		try{
			//the file manager has now replaced the players ability to save
			MidiParser parser = new MidiParser();
		
			LilyPondParserListener listener = new LilyPondParserListener();
			parser.addParserListener(listener);
			parser.parse(MidiSystem.getSequence(inputFile));
			
			String lyString = listener.getLyString();
//			System.out.println(lyString + " awdawda");
			
		MidiFileManager fileManager = new MidiFileManager();
//			//this takes a midi file and renders it as a pattern
//			Pattern midiPattern = fileManager.loadPatternFromMidi(outputMidi);
//			player.getSequence(inputPattern);
//			
		}catch(Exception e){
			e.printStackTrace();
		}
//		
//		File incomingMidi = new File("test.midi");
		
	}

}
