import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	private final static int TXT = 10;

	public static int[] getUserBeat(String[] s) {
		int[] n2 = new int[s.length];
		for (int i = 0; i < s.length; i++)
			n2[i] = Integer.parseInt(s[i]);
		return n2;

	}

	public static void main(String[] args) throws IOException {
		int[] n2 = new int[args.length];
		for (int k = 0; k < 10; k++) {
			double value;
			int minI = -1;
			double minInt = 9999999;

			Compare c = new Compare();

			n2 = Main.getUserBeat(args);
			for (int i = 0; i < TXT; i++) {
				// System.out.println();
				value = c.compareResult(i, n2);
				System.out.println(i + "'s min value is " + value);

				if (minInt > value) {
					minInt = value;
					minI = i;
				}

				/*
				 * IF you want to use 3 similar files, USE this code BELOW. //
				 * gather about 3 similar files if (minCount < 3) {
				 * minInt[minCount++] = value; } else { for (int j = 0; j < 3;
				 * j++) { if (minInt[j] > value) { minInt[j] = value; minArr[j]
				 * = i; break; } } }
				 */
			}
			/*
			 * for (int j = 0; j < 3; j++) { if (fc.getN2File() == minArr[j]) {
			 * correct++; break; } }
			 */
			System.out.println(minI);
		}
	}
}
