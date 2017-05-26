import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.file.*;
import java.util.Random;

public class FileControling {

	private String filename;
	private int n2File;
	
	void randomMapping(int numOfFile) throws IOException {
		int arr[] = new int[1000];
		Random rand = new Random();

		for (int i = 0; i < numOfFile; i++) {
			filename = i + ".txt";
			File file = new File(filename);
			file.createNewFile();

			FileWriter fw = new FileWriter(file);
			for (int j = 0; j < 200; j++) {
				for (int k = 0; k < 1000; k++) {
					fw.write((char) rand.nextInt(10) + '0');
				}
			}
			fw.close();
		}
	}

	int[] getN2(int numOfFile) throws IOException {
		Random rand = new Random();
		int randomI = rand.nextInt(numOfFile);
		int length = 1000;
		char[] tmp = new char[1000];
		int [] n2 = new int[1000];

		filename = randomI + ".txt";
		FileReader reader = new FileReader(filename);
		n2File = randomI;

		// start copy between 0~190000
		randomI = rand.nextInt(190000);
		
		//to get
		randomI = randomI/tmp.length;
		while(randomI>0){
			reader.read(tmp);
			randomI--;
		}
		reader.read(tmp);
			
		for (int i = 0; i < length; i++) {
			if (tmp[i] == '1' || tmp[i] == '0'|| tmp[i] == '2'|| tmp[i] == '3'|| tmp[i] == '4'|| tmp[i] == '5'|| tmp[i] == '6'|| tmp[i] == '7'|| tmp[i] == '8'|| tmp[i] == '9')
				n2[i] = tmp[i] - '0';
		}
		reader.close();
		return n2;
	}
	
	int getN2File(){
		return n2File;
	}

}
