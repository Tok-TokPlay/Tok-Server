import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Compare {

	// seg_length_1000 == n2.size();
	private final static int SEG_LENGTH_1000 = 1000;
	public final static int SEG_LENGTH = SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
	private final static int MIN_ERROR_RANGE = 5;
	private int correctable[] = new int[SEG_LENGTH];

	// I will move the compare 'Main'class's function to here
	double compareResult(int fileNumber, int n2[]) throws IOException {
		double result = -1.0;
		FileControling fc = new FileControling();
		ArrayList<String> n1 = fc.getN1List(fileNumber);
		int segmentedN1[] = new int[SEG_LENGTH];
		int segmentedN1_1000[] = new int[SEG_LENGTH_1000];
		int segSize = n1.size() / SEG_LENGTH_1000;
		// compare each segmentedN1 with n1
		int i = 0;
		double distance;
		int minSeg = 0;
		int SegLocation = segSize;
		int arrLength = 0;
		double[] min = new double[MIN_ERROR_RANGE];
		int[] minSegArr = new int[MIN_ERROR_RANGE];

		while (SegLocation > 0) {
			// if it is the last segment of the arrayList, the last length would
			// be smaller than SEG_LENGTH
			if (SegLocation != 1)
				for (int j = 0; j < SEG_LENGTH; j++, i++) {
					segmentedN1[j] = Integer.parseInt((String) n1.get(i));
				}
			else {
				for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
					segmentedN1_1000[j] = Integer.parseInt((String) n1.get(i));
				}
			}
			// to store again (about seg_length/3 size) at next turn...
			i -= SEG_LENGTH_1000 / 3;

			DTW2 dtw = new DTW2(segmentedN1, n2);
			// finding any SegLocations which makes minimum min value
			// 0,1,2,3,4
			distance = dtw.getDistance();
			if (arrLength < 5) {
				min[arrLength++] = distance;
			} else {
				for (int j = 0; j < MIN_ERROR_RANGE; j++) {
					if (distance < min[j]) {
						min[j] = distance;
						minSegArr[j] = SegLocation;
						break;
					}
				}
			}
			SegLocation--;
		}

		// here is the most similarity part of the arrayList!
		// And I will check more detail around this part.
		int tmpi;
		double min2 = 9999999;
		for (int l = 0; l < MIN_ERROR_RANGE; l++) {
			int k = 0;
			i = (segSize - minSegArr[l] - 1) * SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3); 
			// 3등분해서
			// 지나감
			// Because the 'minSegArr == 1' means the last segment of the
			// n1.
			if (minSegArr[l] == 1) {
				k = 2; // Do this 'for'loop just 3 time!
			} else if (minSegArr[l] == segSize) { // if the similarity part
													// is the first segment
													// of the n1.
				k = 2;
				i = 0;
			}

			for (; k < 5; k++) {
				tmpi = i;
				// <Char> to integer

				/*
				 * Error occur Exception in thread "main"
				 * java.lang.IndexOutOfBoundsException: Index: 200000, Size:
				 * 200000 at java.util.ArrayList.rangeCheck(ArrayList.java:653)
				 * at java.util.ArrayList.get(ArrayList.java:429) at
				 * Compare.compareResult(Compare.java:99) at
				 * Main.main(Main.java:33)
				 * 
				 */
				
				//temporary, I set tmpi boundary
				for (int j = 0; j < SEG_LENGTH_1000 &&tmpi<n1.size(); j++, tmpi++) {
					segmentedN1_1000[j] = Integer.parseInt((String) n1.get(tmpi));
				}
				// check similarity by DTW
				DTW2 dtw1 = new DTW2(segmentedN1_1000, n2);
				// store the minimum result of DTW
				if (min2 > dtw1.getDistance()) {
					min2 = dtw1.getDistance();
					correctable = segmentedN1;
				}
				i = i + n2.length / 3;
			}
		}

		n1.clear();
		result = min2;
		return result;
	}

	int[] getCorrectable() {
		return correctable;
	}
}
