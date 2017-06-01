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

	private String filename;
	private int n2File;
	// This should be deleted in a jarFile@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	private static final int n2Size = 1000;

	void randomMapping(int numOfFile) throws IOException {
		int arr[] = new int[1000];
		Random rand = new Random();

		for (int i = 0; i < numOfFile; i++) {
			String filename = "C:\\Users\\user\\Desktop\\SongData\\" + i
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
		String filename = "C:\\Users\\user\\Desktop\\SongData\\" + fileNum
				+ ".txt";
		// read file and store at Buffer
		BufferedReader in = new BufferedReader(new FileReader(filename));
		while ((temp = in.readLine()) != null) { // read one by one
			n1.add(temp);
		}
		in.close();
		return n1;

	}

	int[] getN2(int numOfFile) throws IOException {

		Random rand = new Random();
		int randomI = rand.nextInt(numOfFile);
		int length = n2Size;
		char[] tmp = new char[1000];
		int[] n2 = new int[n2Size];
		ArrayList n1 = new ArrayList();
		StringTokenizer token;
		ArrayList<String> arrayList = new ArrayList<>();
		int i = 0;

		String filename = "C:\\Users\\user\\Desktop\\SongData\\" + randomI
				+ ".txt";
		// String filename = randomI+".txt";
		// read file and store at Buffer
		BufferedReader in = new BufferedReader(new FileReader(filename));
		n1 = getN1List(randomI);
		System.out.println(n1.size());
		int roundBound = n1.size() - n2.length;
		int pass = rand.nextInt(roundBound);
		System.out.println("pass : " + pass);
		String temp = null;
		// for pass several integer (random amount)
		System.out.println("n2__________");
		int count=0;
		while (pass > 0 && (temp = in.readLine()) != null) {// read one line
			pass--;
			//System.out.println(count++);
			// System.out.println(temp+"_________________________");
		}

		while ((temp = in.readLine()) != null&&i<n2Size) { // read one by one
			n2[i++] = Integer.parseInt(temp);
		}
		in.close(); // 파일 스트림 닫기

		n2File = randomI;
		return n2;
	}

	int getN2File() {
		return n2File;
	}

}
