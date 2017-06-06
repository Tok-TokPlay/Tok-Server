package com.example.dkdk6.toktokplay.Activity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Comparing {
	// segment the N1(original array) with size 1000
	private final static int MIN_ERROR_RANGE = 5;

	private int SEG_LENGTH_1000;
	private int SEG_LENGTH;
	private int correctable[];
	private int fileNumber;
	private String fileName;
	private String filePath;
	private String filePathAndName;
	private ArrayList<String> n1;
	private File[] fileList;
	private String n2s;
	private String musicKey;
	double[] result;
	Comparing(String filePath, String n2s) throws IOException {
		this.n2s = n2s;
		SEG_LENGTH_1000 = n2s.length();
		SEG_LENGTH = SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
		correctable = new int[SEG_LENGTH];

		this.filePath = filePath;
		File path = new File(filePath);
		String files[] = path.list();
		fileList = path.listFiles();
		fileNumber = files.length;

		//fileName = filePath + "\\" + fileNumber + ".txt";
		
		compare();
	}

	void compare() throws IOException {
		result = new double[fileNumber];
		for (int fNum = 0; fileNumber> fNum ; fNum++) {
			fileName = fileList[fNum].getName();
			filePathAndName = filePath + "\\" + fileName;
			n1 = getN1();
			// to compare each segmentedN1 with n1
			int segmentedN1[] = new int[SEG_LENGTH];
			int segmentedN1_1000[] = new int[SEG_LENGTH_1000];
			int segSize = n1.size() / SEG_LENGTH_1000;

			int i = 0;
			double distance;
			int SegLocation = segSize;
			int arrLength = 0;
			int[] n2 = new int[n2s.length()];
			double[] min = new double[MIN_ERROR_RANGE];
			int[] minSegArr = new int[MIN_ERROR_RANGE];

			while (SegLocation > 0) {
				if (SegLocation != 1) {
					for (int j = 0; j < SEG_LENGTH; j++, i++) {
						segmentedN1[j] = (int)Float.parseFloat(n1.get(i));
					}
				}
				// if it is the last segment of the arrayList, the last length
				// would
				// be smaller than SEG_LENGTH -> so SEG_LENGTH
				else {
					for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
						segmentedN1_1000[j] = (int)Float.parseFloat(n1.get(i));
					}
				}
				// to store again (about seg_length/3 size) at next turn...
				i -= SEG_LENGTH_1000 / 3;

				// n2s to n2
				for (int j = 0; i < n2.length; i++) {
					n2[j] = n2s.charAt(j) - '0';
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
			//I wanted to make this another function but there is no time........
			int tmpi;
			double min2 = 9999999;

			for (int l = 0; l < MIN_ERROR_RANGE; l++) {
				int k = 0;
				i = (segSize - minSegArr[l] - 1) * SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
				// Because the 'minSegArr == 1' means the last segment of the
				// n1.
				if (minSegArr[l] == 1) {
					k = 2; // Do this 'for'loop just 3 time!
				} else if (minSegArr[l] == segSize) { // if the similarity part
														// is
														// the first segment of
														// the
														// n1.
					k = 2;
					i = 0;
				}

				for (; k < 5; k++) {
					tmpi = i;
					// <Char> to integer
					// temporary, I set tmpi boundary
					for (int j = 0; j < SEG_LENGTH_1000 && tmpi < n1.size(); j++, tmpi++) {
						segmentedN1_1000[j] = (int)Float.parseFloat(n1.get(tmpi));
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
			n1.clear();
			result[fNum] = min2;
		}
		double minResult=9999999;//to make max value;
		int minI = 0;
		for(int i=0;i<fileNumber;i++){
			if(minResult>result[i]){
				minI=i;
				minResult = result[i];
			}
		}
		musicKey = fileList[minI].getName();
	}
	// I will move the compare 'Main'class's function to here.
	ArrayList<String> getN1() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filePathAndName));
		String temp;
		ArrayList<String> arrayList = new ArrayList<String>();
		while ((temp = in.readLine()) != null) {
			arrayList.add(temp);
		}
		in.close();
		return arrayList;
	}

	String getMusicKey() {
		for(int i = 0; i < result.length; i++){
			System.out.println(result[i]);
		}
		return musicKey;
	}
	int[] getCorrectable() {
		return correctable;
	}
}
