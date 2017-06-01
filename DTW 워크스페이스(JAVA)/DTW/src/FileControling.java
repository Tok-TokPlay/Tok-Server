import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class FileControling {

	private int n2File;
	// This should be deleted in a jarFile@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	private static final int n2Size = 1000;

	void randomMapping(int numOfFile) throws IOException {
		int arr[] = new int[1000];
		Random rand = new Random();

		for (int i = 0; i < numOfFile; i++) {
			String filename = "C:\\Users\\5p\\Desktop\\SongData\\" + i
					+ ".txt";
			File file = new File(filename);
			file.createNewFile();

			FileWriter fw = new FileWriter(file);
			for (int j = 0; j < 200; j++) {
				for (int k = 0; k < 1000; k++) { // for testing, I recommend not
													// 1000, just 100... not to
													// bumb...
					int z = rand.nextInt(1000);
					fw.write(Integer.toString(z));
					fw.write("\r\n");
				}
			}
			fw.close();
		}
	}

	ArrayList<String> getN1List(int fileNum) throws IOException {
		String temp;
		ArrayList<String> n1 = new ArrayList<String>();
		String filename = "C:\\Users\\5p\\Desktop\\SongData\\" + fileNum
				+ ".txt";
		// read file and store at Buffer
		FileReader fr = new FileReader(filename);
		BufferedReader in = new BufferedReader(fr);
		while ((temp = in.readLine()) != null) { // read one by one
			n1.add(temp);
		}
		fr.close();
		in.close();
		return n1;

	}
//Not Use when execute in the jar file... (for testing only)
	int[] getN2(int numOfFile) throws IOException {

		Random rand = new Random();
		int randomI = rand.nextInt(numOfFile);
		int[] n2 = new int[n2Size];
		ArrayList<String> n1 = new ArrayList<>();
		int i = 0;

		String filename = "C:\\Users\\5p\\Desktop\\SongData\\" + randomI
				+ ".txt";

		FileReader fr = new FileReader(filename);
		BufferedReader in = new BufferedReader(fr);
		n1 = getN1List(randomI);
		int roundBound = n1.size() - n2.length;
		int pass = rand.nextInt(roundBound);
		String temp = null;
		// for pass several integer (random amount)
		while (pass > 0 && (temp = in.readLine()) != null) {// read one line
			pass--;
		}

		while ((temp = in.readLine()) != null&&i<n2Size) { // read one by one
			n2[i++] = Integer.parseInt(temp);
		}
		fr.close();
		in.close(); // 파일 스트림 닫기

		n2File = randomI;
		return n2;
	}

	int getN2File() {
		return n2File;
	}

}
