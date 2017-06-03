import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
					int z = rand.nextInt(1000);
					fw.write(Integer.toString(z));
					fw.write(",");
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
		int[] n2 = new int[1000];
		String nums=null;
		filename = randomI + ".txt";
		FileReader reader = new FileReader(filename);
		n2File = randomI;

		// start copy between 0~190000
		randomI = rand.nextInt(190000);

		// to get
		randomI = randomI / tmp.length;
		while (randomI > 0) {
			reader.read(tmp);
			randomI--;
		}
		reader.read(tmp);

		for (int i = 0,j=0; i < length; i++) {
			nums="";
			System.out.print(i);
			while(tmp[i]!=',' && i<length){
				nums += Character.toString(tmp[i++]);
			}
			n2[j++] = Integer.parseInt(nums);
		}
		reader.close();
		return n2;
	}

	int getN2File() {
		return n2File;
	}

}
