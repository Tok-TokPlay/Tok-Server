package com.example.dkdk6.toktokplay.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.example.dkdk6.toktokplay.Activity.dtw.FastDTW;
import com.example.dkdk6.toktokplay.Activity.dtw.TimeWarpInfo;
import com.example.dkdk6.toktokplay.Activity.timeseries.TimeSeries;
import com.example.dkdk6.toktokplay.Activity.util.DistanceFunctionFactory;

public class ComparingWithFastDTW {
	// segment the N1(original array) with size 1000
	private final static int MIN_ERROR_RANGE = 5;

	private int SEG_LENGTH_1000;
	private int SEG_LENGTH;
	private int correctable[];
	private int fileNumber;
	private String fileName;
	private String filePath;
	private String filePathAndName;
	private File[] fileList;
	private String n2s;
	private String musicKey;
	double[] result;

	ComparingWithFastDTW(String filePath, String n2s) throws IOException {
		this.n2s = n2s;
		// testing if the userBeat correctly comes.
		for (int i = 0; i < n2s.length(); i++) {
			System.out.print(n2s.charAt(i) - '0');
		}
		System.out.println("^n2");
		SEG_LENGTH_1000 = n2s.length();
		SEG_LENGTH = SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
		correctable = new int[SEG_LENGTH];

		this.filePath = filePath;
		File path = new File(filePath);
		String files[] = path.list();
		fileList = path.listFiles();
		fileNumber = files.length;

		// fileName = filePath + "\\" + fileNumber + ".txt";

		System.gc();
		compare();
	}

	void compare() throws IOException {
		result = new double[fileNumber];
		for (int fNum = 0; fNum < fileNumber; fNum++) {
			System.out.println((fNum + 1) + " th file Checking now...");

			fileName = fileList[fNum].getName();
			filePathAndName = filePath + "\\" + fileName;
			result[fNum] = compareFile(filePathAndName);
		}
		double minResult = 999999999;// to make max value;
		int minI = 0;
		for (int i = 0; i < fileNumber; i++) {
			if (minResult > result[i]) {
				minI = i;
				minResult = result[i];
			}
		}
		musicKey = fileList[minI].getName();
	}

	double compareFile(String fileName) throws IOException {
		ArrayList<String> n1 = getN1();
		// to compare each segmentedN1 with n1
		double segmentedN1[] = new double[SEG_LENGTH];
		double segmentedN1_1000[] = new double[SEG_LENGTH_1000];
		int segSize = n1.size() / SEG_LENGTH_1000;
		int i = 0;
		double distance;
		int SegLocation = segSize;
		int arrLength = 0;
		double[] n2 = new double[n2s.length()];
		double[] min = new double[MIN_ERROR_RANGE];
		int[] minSegArr = new int[MIN_ERROR_RANGE];
		TimeSeries tsN1;
		TimeSeries tsN2;

		while (SegLocation > 0) {
			if (SegLocation != 1) {
				for (int j = 0; j < SEG_LENGTH; j++, i++) {
					segmentedN1[j] = (double) Float.parseFloat(n1.get(i));
				}
			}
			// if it is the last segment of the arrayList, the last length
			// would be smaller than SEG_LENGTH -> so SEG_LENGTH
			else {
				for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
					segmentedN1_1000[j] = (double) Float.parseFloat(n1.get(i));
				}
			}
			// to store again (about seg_length/3 size) at next turn...
			i -= SEG_LENGTH_1000 / 3;

			// n2s to n2
			for (int j = 0; i < n2.length; i++) {
				n2[j] = n2s.charAt(j) - '0';
				// n2[j] *= meanOfN1;
			}

			// finding any SegLocations which makes minimum min value
			// 0,1,2,3,4
			System.gc();

			tsN1 = new TimeSeries(zNormalization(segmentedN1));
			tsN2 = new TimeSeries(n2);
			distance = FastDTW.getWarpDistBetween(tsN1, tsN2,
					DistanceFunctionFactory.getDistFnByName("EuclideanDistance"));
			System.gc();
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
		// I wanted to make this another function but there is no time........
		int tmpi;
		double min2 = 999999999;

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
					segmentedN1_1000[j] = (int) Float.parseFloat(n1.get(tmpi));
					System.gc();
				}
				// check similarity by DTW
				System.gc();
				tsN1 = new TimeSeries(zNormalization(segmentedN1_1000));
				tsN2 = new TimeSeries(n2);
				distance = FastDTW.getWarpDistBetween(tsN1, tsN2,
						DistanceFunctionFactory.getDistFnByName("EuclideanDistance"));

				System.gc();
				// store the minimum result of DTW
				if (min2 > distance) {
					min2 = distance;
				}
				i = i + n2.length / 3;
			}
		}
		n1.clear();
		return min2;
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

	double[] zNormalization(double[] T) {
		double[] result = new double[T.length];
		double sum = 0;

		for (int i = 0; i < T.length; i++) {
			sum = sum + T[i];
		}
		double mean = sum / T.length;

		sum = 0;
		for (int i = 0; i < T.length; i++) {
			sum = sum + (T[i] - mean) * (T[i] - mean);
		}
		double std = sum / T.length;
		std = Math.sqrt(std);

		for (int i = 0; i < T.length; i++) {
			result[i] = (T[i] - mean) / std;
		}

		return result;
	}

	String getMusicKey() {
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		return musicKey;
	}

	int[] getCorrectable() {
		return correctable;
	}

}
