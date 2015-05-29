package birdSong;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class midiConversionAndCommand {
	public static String fileName = "";
	public static File saveTo = new File(fileName);
	public static String midiLocation = "";
	public static final String mScore = "\"C:/Program Files (x86)/MuseScore 2/bin/MuseScore.exe\" ";
	
	@SuppressWarnings("static-access")
	public midiConversionAndCommand(String midiFilePath){
		this.fileName = midiFilePath + ".mid";
		this.saveTo = new File(fileName);
		this.midiLocation = "\"C:/Users/Tyler/workspace/birdSong/\"" + fileName;
	}
	
	@SuppressWarnings("static-access")
	public static void convertToMidi(Pattern completeSong) throws IOException{
		MidiFileManager mfm = new MidiFileManager();
		Player player = new Player();
		mfm.save(player.getSequence(completeSong), saveTo);
		System.out.println("File Converted to MIDI");
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public static void convertMidiToXML() throws IOException{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c", mScore  + midiLocation + " -o Score.xml");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		FileChannel src = new FileInputStream(new File("Score.xml")).getChannel();
		FileChannel dest = new FileOutputStream(new File("C:/wamp/www/Practice/TwitterCrawlr/Crawlr/musicXML/score-viewer/musicxml/Score.xml")).getChannel();
		dest.transferFrom(src, 0, src.size());
		dest.close();
		src.close();
		System.out.println("File Converted to MusicXML and copied");
	}
}
