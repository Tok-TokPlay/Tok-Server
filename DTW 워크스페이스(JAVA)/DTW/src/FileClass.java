import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.Random;

public class FileClass {

	private String filename;
	void randomMapping(int numOfFile) throws IOException{
		int arr[] = new int[1000];
		Random rand = new Random();
		
		for(int i=0;i<numOfFile; i++){
			filename = i+".txt";
			File file = new File(filename);
			file.createNewFile();
			
			FileWriter fw = new FileWriter(file);
			for(int j=0;j<200;j++){
				for(int k=0;k<1000;k++){
					fw.write((char)rand.nextInt(2)+'0');
				}
			}
			fw.close();
		}
	}
	void fileOpen(int numOfFile) throws FileNotFoundException{
		for(int i=0; i<numOfFile; i++){
			filename = i+".txt";
			FileReader reader = new FileReader(filename);
		}
		
		
	}
}
