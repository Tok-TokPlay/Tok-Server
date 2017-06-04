import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Compare {
	// segment the N1(original array) with size 1000
	private final static int MIN_ERROR_RANGE = 5;

	private int SEG_LENGTH_1000;
	private int SEG_LENGTH;
	private int correctable[];
	private int fileNumber;
	private String fileName;
	private ArrayList<String> n1;
	private String n2s;

	Compare(String filePath, String n2s) {
		this.n2s = n2s;
		SEG_LENGTH_1000 = n2s.length();
		SEG_LENGTH = SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
		correctable = new int[SEG_LENGTH];

		File path = new File(filePath);
		String files[] = path.list();
		fileNumber = files.length;
		
		fileName = filePath + "\\" + fileNumber + ".txt";
	}

	// I will move the compare 'Main'class's function to here.
	ArrayList<String> getN1() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String temp;
		ArrayList<String> arrayList = new ArrayList<String>();
		while ((temp = in.readLine()) != null) {
			arrayList.add(temp);
		}
		in.close();
		return arrayList;
	}

	double compare() throws IOException {
		double result = -1.0;
		for (int fNum = fileNumber; fNum > 0; fNum--) {
			n1 = getN1();
		}
		
		int segmentedN1[] = new int[SEG_LENGTH];
		int segmentedN1_1000[] = new int[SEG_LENGTH_1000];
		int segSize = n1.size() / SEG_LENGTH_1000;

		// compare each segmentedN1 with n1
		int i = 0;
		double distance;
		int SegLocation = segSize;
		int arrLength = 0;
		int[] n2 = new int[n2s.length()];
		double[] min = new double[MIN_ERROR_RANGE];
		int[] minSegArr = new int[MIN_ERROR_RANGE];

		while (SegLocation > 0) {
			// if it is the last segment of the arrayList, the last length would
			// be smaller than SEG_LENGTH
			if (SegLocation != 1) {
				for (int j = 0; j < SEG_LENGTH; j++, i++) {
					segmentedN1[j] = Integer.parseInt(n1.get(i));
				}
			} else {
				for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
					segmentedN1_1000[j] = Integer.parseInt(n1.get(i));
				}
			}
			// to store again (about seg_length/3 size) at next turn...
			i -= SEG_LENGTH_1000 / 3;

			// n2ArrayList to n2
			for (int j = 0; i < n2.length; i++) {
				n2[j] = Integer.parseInt(n2.)
			}
			DTW2 dtw = new DTW2(segmentedN1, n2);
			// finding any SegLocations which makes minimum min value
			// 0,1,2,3,4
			distance = dtw.getInverseSimilarity();
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
		double min2 = 100;

		for (int l = 0; l < MIN_ERROR_RANGE; l++) {
			int k = 0;
			i = (segSize - minSegArr[l] - 1) * SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
			// Because the 'minSegArr == 1' means the last segment of the
			// n1.
			if (minSegArr[l] == 1) {
				k = 2; // Do this 'for'loop just 3 time!
			} else if (minSegArr[l] == segSize) { // if the similarity part is
													// the first segment of the
													// n1.
				k = 2;
				i = 0;
			}

			for (; k < 5; k++) {
				tmpi = i;
				// <Char> to integer
				// temporary, I set tmpi boundary
				for (int j = 0; j < SEG_LENGTH_1000 && tmpi < 200000; j++, tmpi++) {
					segmentedN1_1000[j] = Integer.parseInt(arrayList.get(tmpi));
				}
				// check similarity by DTW
				DTW2 dtw1 = new DTW2(segmentedN1_1000, n2);
				// store the minimum result of DTW
				if (min2 > dtw1.getInverseSimilarity()) {
					min2 = dtw1.getInverseSimilarity();
					correctable = segmentedN1;
				}
				i = i + n2.length / 3;
			}
		}

		arrayList.clear();
		result = min2;
		reader.close();

		return result;
	}

	int[] getCorrectable() {
		return correctable;
	}
}
