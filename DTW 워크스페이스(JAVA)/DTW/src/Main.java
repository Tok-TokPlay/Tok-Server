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
			double minInt = 9999999;

			FileControling fc = new FileControling();
			Compare c = new Compare();
			int[] correctable = new int[Compare.SEG_LENGTH];

			fc.randomMapping(TXT); // Make txt file (number of TXT) n2 =
			System.out.println("txt파일 생성");
			n2 = fc.getN2(TXT);
			for (int i = 0; i < TXT; i++) {
				// System.out.println();
				value = c.compareResult(i, n2);
				System.out.println(i + "'s min value is " + value);

				if(minInt>value){
					minInt = value;
					minI = i;
				}
				/*			
				// gather about 3 similar files
				if (minCount < 3) {
					minInt[minCount++] = value;
				} else {
					for (int j = 0; j < 3; j++) {
						if (minInt[j] > value) {
							minInt[j] = value;
							minArr[j] = i;
							break;
						}
					}
				}
				*/
			}
			/*
			for (int j = 0; j < 3; j++) {
				if (fc.getN2File() == minArr[j]) {
					correct++;
					break;
				}
			}
			 */
			count++;
			if(minI == fc.getN2File())
				correct++;
			System.out.println("n2 was in " + fc.getN2File());
		}
		System.out.println();
		System.out
				.println("---------------------------------------------------------------");
		System.out.println("Accuracy : " + (float) correct / count*100 +"%");
	}
}
