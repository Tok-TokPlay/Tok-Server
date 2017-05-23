import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class FileClass {

	private String filename;
	void randomMapping(int numOfFile) throws IOException{
		int arr[] = new int[1000];
		for(int i=0;i<numOfFile; i++){
			filename = i+".txt";
			File file = new File(filename);
			file.createNewFile();
			
			FileWriter fw = new FileWriter(file);
			for(int j=0;j<200;j++){
				for(int k=0;k<1000;k++){
					ran
				}
			}
			
		}
	}
	void fileOpen(int numOfFile) throws FileNotFoundException{
		for(int i=0; i<numOfFile; i++){
			filename = i+".txt";
			FileReader reader = new FileReader(filename);
		}
		
		
	}
}
