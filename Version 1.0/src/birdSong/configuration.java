package birdSong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class configuration {
	public static String[] secureCreds(){
		File file = new File("config.txt");
		String[] creds = new String[3];
		try{
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line = "";
			
			int count = 0;
			while((line = bf.readLine()) != null){
				creds[count] = line;
				count++;
			}
			bf.close();		
		}catch(Exception e){
			
		}
		return creds;
	}

}
