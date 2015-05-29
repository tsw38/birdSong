package birdSong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.DoubleStream;

public class buildMarkovSong {

	public static HashMap<String, double[]> markovMatrix(String sentence, String type){
		String adjusted = null;
		Set<String> seed = null;
		switch(type){
		case "notes":
			adjusted = sentence.replaceAll("[a-z0-9./:@MTVItKOY, \\)\\(]", "");
			seed = new HashSet<String>(Arrays.asList(adjusted.split("")));
//			System.out.println("value of notes: " + seed);
			break;
		case "durations":
			adjusted = sentence.replaceAll("(a)[0-9]+(d)[0-9],\\(\\)", "").replaceAll("[/:@MTVIY A-Zb0-9#tKO]", "");
			seed = new HashSet<String>(Arrays.asList(adjusted.split("")));
//			System.out.println("value of seed: " + seed);
			break;
		case "pitches":
			adjusted = sentence.replaceAll("@[0-9]+.[0-9]+\\b\\s,+", "").replaceAll("[TIME:]+[0-9]+[\\/][0-9]+\\s+", "")
					.replaceAll("[V]+[0-9]+\\s+", "").replaceAll("[I]+[0-9]+\\s+", "")
					.replaceAll("(:CON)\\([0-9]+\\,[0-9]+\\)\\s+", "").replaceAll("T[0-9]+\\s+", "")
					.replaceAll("KEY\\:\\w+", "").replaceAll("\\s+", " ").replaceAll("a[0-9]+d[0-9]+", "")
					.replaceAll("\\/[0-9]+\\.[0-9]+", "").replaceAll("[^0-9]", "");
			seed = new HashSet<String>(Arrays.asList(adjusted.split("")));
			break;
		}
		
		//this is a set of all the "notes" in a song
		
		Set<String> transition = seed;
		HashMap<String, double[]> noteOccur = new HashMap<String, double[]>();
		
		System.out.println("Sentence: " + adjusted);
		
			for(String note : seed){
//				System.out.println("seed: " + note);
				double[] count = new double[seed.size()];
				int countIterator = 0;
				for(String note2 : transition){
//					System.out.println("bigram " + note + note2);
					int counter = 0;
					for(int i = 0; i<=adjusted.length()-1; i++){
						if(i != adjusted.length()-1){
							char fir = adjusted.charAt(i);
							char sec = adjusted.charAt(i+1);
	//						System.out.println(fir + " " + sec);
							if(fir == note.charAt(0) && sec == note2.charAt(0)){
								counter++;
							}
						} 					
					}
					count[countIterator] = counter;
					countIterator++;
				}
				noteOccur.put(note, count);
			}
		
			
			
			
			
			
		for(String noteName : noteOccur.keySet()){
			double[] entries = noteOccur.get(noteName);
			double sum = DoubleStream.of(entries).sum();
			
			for(int i = 0; i< entries.length; i++){
				entries[i] /= sum;
			}
			noteOccur.replace(noteName, entries);
		}
		return noteOccur;
	}

	public static String getFirstNote(HashMap<String, double[]> markov){
		String[] notes = markov.keySet().toArray(new String[markov.keySet().size()]);
		int ranNoteIndex = new Random().nextInt(notes.length);
		return (notes[ranNoteIndex] == "#") ? notes[ranNoteIndex] : notes[0];
	}
	
	public static int getNextNoteIndex(double[] noteArray){
		double randomNum = Math.random();
		
		double distance = Math.abs(noteArray[0] - randomNum);
		int index = 0;
		
		for(int c = 1; c < noteArray.length; c++){
			double cDistance = Math.abs(noteArray[c] - randomNum);
			if(cDistance < distance){
				index = c;
				distance = cDistance;
			}
		}
		return (noteArray[index] == Double.NaN) ? 0 : index;
	}

	public static String createNoteString(int songLength, HashMap<String, double[]> markov, String type){
		String[] notesInSong = markov.keySet().toArray(new String[markov.keySet().size()]);
		String song = "";
		String firstNote = getFirstNote(markov);
		String nextNote = notesInSong[getNextNoteIndex(markov.get(firstNote))];
		
		song += firstNote + " ";
		for(int i = 0; i<songLength-1; i++){
			nextNote = notesInSong[getNextNoteIndex(markov.get(firstNote))];
			
			song += notesInSong[getNextNoteIndex(markov.get(firstNote))] + " ";
			firstNote = nextNote;
		}
		switch(type){
		case "notes":
			if(song.contains("#")){
				song = song.replaceAll("[ ]+\\#\\)\\(", "#");
			}
			break;
		case "durations":
			if(song.contains(".")){
				song = song.replaceAll("[ ]+\\.\\)\\(", ".");
			}
			break;
		}
		return song;
	}

	public static List<String> createVoices(String song, int lengthOfSong, int numOfVoices){
        
        List<String> voiceList = new ArrayList<String>();
        for(int j=0; j<numOfVoices; j++){
        	HashMap<String, double[]> p1 = markovMatrix(song, "notes");
            HashMap<String, double[]> p2 = markovMatrix(song, "durations");
            HashMap<String, double[]> p3 = markovMatrix(song, "pitches");
            
            String[] output = createNoteString(lengthOfSong, p1, "notes").split(" ");
            String[] output2 = createNoteString(lengthOfSong, p2, "durations").split(" ");
            String[] output3 = createNoteString(lengthOfSong, p3, "pitches").split(" ");

            
            for(int i = 0; i <= output.length-1; i++){
            	boolean inBound = (i >= 0) && (i < output2.length);
            	if(inBound){
            		output[i] = output[i] + output2[i];
            	} else {
            		output[i] = output[i] + output2[0];
            	}
            }
            voiceList.add(String.join(" ", output));
        }
        
        
        return voiceList;
	}
	
	public static String generateCompleteSong(String polarity, List<String> voices){
		Random init = new Random();
		String instrument = "";
		int low = 0;
		int high = 0;
		int rand = 0;
		
		for(int k = 0; k< voices.size(); k++){
		switch(polarity){
		case "negative":
			low = 100; high = 120;
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
			low = 120; high = 180;
			rand = init.nextInt(high - low) + low;
			String[] posInstruments ={"Piano", "Flute", "Clarinet", "Oboe", "Soprano_Sax", "Glockenspiel", "Tinkle_Bell", "Piccolo", "Tubular_Bells", "Recorder", "Whistle", "Bird_Tweet"};
			instrument = posInstruments[init.nextInt(posInstruments.length)];
			break;
		}
			String voice = voices.get(k);
			voices.set(k, "V" + k + " I[" + instrument + "] " + voice);
		}
		
		String result = "T" + rand + " " + String.join(" ", voices);
		return result;
	}
}
