import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	private final static int TXT = 10;

	public static void main(String[] args) throws IOException {
		int count = 0;
		int correct = 0;

		for (int k = 0; k < 10; k++) {
			System.out.println(k + "OOOOOOOOOOOO");
			float num;
			int n2[] = new int[1000];
			double value;
			double min[] = new double[3];
			int minI = -1;
			int minArr[] = new int[3];
			int minCount = 0;

			FileControling fc = new FileControling();
			Compare c = new Compare();
			int[] correctable = new int[Compare.SEG_LENGTH];

			fc.randomMapping(TXT); // Make txt file (number of TXT) n2 =
			n2 = fc.getN2(TXT);
			/*
			 * for (int i = 0; i < n2.length; i++) System.out.print((int)
			 * n2[i]); System.out.println();
			 */
			for (int i = 0; i < TXT; i++) {
				// System.out.println();
				value = c.compareResult(i, n2);
				System.out.println(i + "'s min value is " + value);
				/*
				 * if (value == 0) { correctable = c.getCorrectable();
				 * System.out.println(i + "'s correctable is "); for (int j = 0;
				 * j < Compare.SEG_LENGTH; j++) {
				 * System.out.print(correctable[j]); } System.out.println(); }
				 */

				// gather about 3 similar files
				if (minCount < 3) {
					min[minCount++] = value;
				} else {
					for (int j = 0; j < 3; j++) {
						if (min[j] > value) {
							min[j] = value;
							minArr[j] = i;
							break;
						}
					}
				}
			}
			for (int j = 0; j < 3; j++) {
				if (fc.getN2File() == minArr[j]) {
					correct++;
					break;
				}
			}
			count++;
			System.out.println("n2 was in " + fc.getN2File());
		}
		System.out.println();
		System.out.println("---------------------------------------------------------------");
		System.out.println("Accuracy : " + (float) correct / count *100 +"%");
		// System.out.println("min value is " + min);
		// System.out.println("the result is " + minI);

		/*
		 * // skip the last binary numbers...
		 * 
		 * // This code is to practice the original DTW
		 * 
		 * ArrayList<Character> arrayList = new ArrayList<Character>(); String
		 * filename = "ww.txt"; FileInputStream fis = new
		 * FileInputStream(filename); BufferedReader reader = new
		 * BufferedReader(new InputStreamReader(fis)); while ((num =
		 * reader.read()) != -1) { if (num == '1' || num == '0')
		 * arrayList.add((char) num); } reader.close(); fis.close();
		 * 
		 * ArrayList<Character> arrayList2 = new ArrayList<>(); FileReader
		 * reader1 = new FileReader("ww2.txt"); while ((num = reader1.read()) !=
		 * -1) { if (num == '1' || num == '0') arrayList2.add((char) num); }
		 * reader1.close();
		 * 
		 * // (parameter)% of User's beat should be chosen to n1_sag float n1[]
		 * = new float[arrayList.size()]; float n2[] = new
		 * float[arrayList2.size()]; for (int i = 0; i < arrayList2.size(); i++)
		 * { n2[i] = arrayList2.get(i) - '0'; } for (int i = 0; i <
		 * arrayList.size(); i++) { n1[i] = arrayList.get(i) - '0'; } DTW2 dtw =
		 * new DTW2(n1, n2); System.out.println(dtw.getDistance());
		 * arrayList2.clear(); arrayList.clear();
		 */
	}
}
