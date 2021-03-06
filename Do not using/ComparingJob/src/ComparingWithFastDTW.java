import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.dtw.FastDTW;
import com.dtw.TimeWarpInfo;
import com.timeseries.TimeSeries;
import com.util.DistanceFunctionFactory;

public class ComparingWithFastDTW {
	// segment the N1(original array) with size 1000
	private final static int MIN_ERROR_RANGE = 5;

	private int SEG_LENGTH_N2SIZE;
	private int SEG_LENGTH;
	private int correctable[];
	private int NumberOfFile;
	private String fileName;
	private String filePath;
	private static String filePathAndName;
	private File[] fileList;
	private String n2s;
	private String musicKey;
	private double[] likelihood0;
	private double[] likelihood1;
	private double[] likelihood2;
	private double[] likelihood3;
	private double[] likelihood4;
	private double[] result;
	private int min_l;

	ComparingWithFastDTW(String filePath, String n2s) throws IOException {
		this.n2s = n2s;
		// testing if the userBeat correctly comes.
		SEG_LENGTH_N2SIZE = n2s.length();
		SEG_LENGTH = SEG_LENGTH_N2SIZE + (SEG_LENGTH_N2SIZE / 3);
		correctable = new int[SEG_LENGTH];

		this.filePath = filePath;
		File path = new File(filePath);
		String files[] = path.list();
		fileList = path.listFiles();
		NumberOfFile = files.length;

		// fileName = filePath + "\\" + fileNumber + ".txt";
	}
	/*
	void compare() throws IOException {
		result = new double[NumberOfFile];
		for (int fNum = 0; fNum < NumberOfFile; fNum++) {
			System.out.println((fNum + 1) + " th file Checking now...");

			fileName = fileList[fNum].getName();
			filePathAndName = filePath + "\\" + fileName;
			//result[fNum] = compareFile(filePathAndName,userBeat);
		}
		double minResult = 999999999;// to make max value;
		int minI = 0;
		for (int i = 0; i < NumberOfFile; i++) {
			if (minResult > result[i]) {
				minI = i;
				minResult = result[i];
			}
		}
		musicKey = fileList[minI].getName();
	}
	*/
	public double compareFile(String fileName,String userBeat) throws IOException {
		this.filePathAndName = this.filePath + "\\" + fileName;
		ArrayList<String> n1 = getN1();
		// to compare each segmentedN1 with n1
		double segmentedN1_long[] = new double[SEG_LENGTH];
		double segmentedN1_sizeN2[] = new double[SEG_LENGTH_N2SIZE];
		int segSize = n1.size() / SEG_LENGTH_N2SIZE;
		int i = 0;
		double distance;
		double[] DUserBeat = new double[userBeat.length()];
		int SegLocation = segSize;
		int arrLength = 0;
		double[] min = new double[MIN_ERROR_RANGE];
		int[] minSegArr = new int[MIN_ERROR_RANGE];
		TimeSeries tsN1;
		TimeSeries tsN2;

		//String -> double userBeat
		for(int tmp=0;tmp<userBeat.length();tmp++)
			DUserBeat[tmp]=userBeat.charAt(tmp);
		
		while (SegLocation > 0) {
			if (SegLocation != 1) {
				for (int j = 0; j < SEG_LENGTH; j++, i++) {
					segmentedN1_long[j] = (double) Float.parseFloat(n1.get(i));
				}
				tsN1 = new TimeSeries(zNormalization(segmentedN1_long));
				tsN2 = new TimeSeries(zNormalization(DUserBeat));
				distance = FastDTW.getWarpDistBetween(tsN1, tsN2,
						DistanceFunctionFactory.getDistFnByName("EuclideanDistance"));
			}
			// if it is the last segment of the arrayList, the last length
			// would be smaller than SEG_LENGTH -> so SEG_LENGTH
			else {
				for (int j = 0; j < SEG_LENGTH_N2SIZE; j++, i++) {
					segmentedN1_sizeN2[j] = (double) Float.parseFloat(n1.get(i));
				}
				tsN1 = new TimeSeries(zNormalization(segmentedN1_sizeN2));
				tsN2 = new TimeSeries(zNormalization(DUserBeat));
				distance = FastDTW.getWarpDistBetween(tsN1, tsN2,
						DistanceFunctionFactory.getDistFnByName("EuclideanDistance"));
			}
			// to store again (about seg_length/3 size) at next turn...
			i -= SEG_LENGTH_N2SIZE / 3;

			// finding any SegLocations which makes minimum min value
			// 0,1,2,3,4
			//set 1,2,3,4,5th 
			if (arrLength < 5) {
				setLikelihood(arrLength, segmentedN1_long);
				min[arrLength++] = distance;
			} else {
				for (int j = 0; j < MIN_ERROR_RANGE; j++) {
					if (distance < min[j]) {
						if(SegLocation != 1)
							setLikelihood(j, segmentedN1_long);
						else
							setLikelihood(i, segmentedN1_sizeN2);
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
		double min2 = 99999999;

		for (int l = 0; l < MIN_ERROR_RANGE; l++) {
			if(min2>min[l]){
				min2 = min[l];
				min_l = l;
			}
			int k = 0;
			i = (segSize - minSegArr[l] - 1) * SEG_LENGTH_N2SIZE + (SEG_LENGTH_N2SIZE / 2);
			// Because the 'minSegArr == 1' means the last segment of the
			// n1.
			if (minSegArr[l] == 1) {
				k = 1; // Do this 'for'loop just 3 time!
			} else if (minSegArr[l] == segSize) { 
			// if the similarity part is the first segment of the n1.
				k = 1;
				i = SEG_LENGTH_N2SIZE / 2;
			}

			for (; k < 2; k++) {
				tmpi = i;
				// <Char> to integer
				// temporary, I set tmpi boundary
				for (int j = 0; j < SEG_LENGTH_N2SIZE && tmpi < n1.size(); j++, tmpi++) {
					segmentedN1_sizeN2[j] = (int) Float.parseFloat(n1.get(tmpi));
				}
				// check similarity by DTW
				tsN1 = new TimeSeries(zNormalization(segmentedN1_sizeN2));
				tsN2 = new TimeSeries(zNormalization(DUserBeat));
				distance = FastDTW.getWarpDistBetween(tsN1, tsN2,
						DistanceFunctionFactory.getDistFnByName("EuclideanDistance"));

				System.gc();
				// store the minimum result of DTW
				if (min2 > distance) {
					setLikelihood(l, segmentedN1_sizeN2);
					min_l = l;
					min2 = distance;
				}
				i = i + DUserBeat.length;
			}
		}
		n1.clear();
		System.out.println(fileName +"      _min :"+min2);
		return min2;
	}

	// I will move the compare 'Main'class's function to here.
	static ArrayList<String> getN1() throws IOException {
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

	void setLikelihood(int n,double[] userbeat){
		switch(n){
		case 0:
			likelihood0 = userbeat;
			break;
		case 1:
			likelihood1 = userbeat;
			break;
		case 2:
			likelihood2 = userbeat;
			break;
		case 3:
			likelihood3 = userbeat;
			break;
		case 4:
			likelihood4 = userbeat;
			break;
		}
	}
	String getLikelihood(){
		switch(min_l){
		case 0:
			return likelihood0.toString();
		case 1:
			return likelihood1.toString();
		case 2:
			return likelihood2.toString();
		case 3:
			return likelihood3.toString();
		case 4:
			return likelihood4.toString();
		}
		return "Likelihood Error";
	}
	String getMusicKey() {
		return musicKey;
	}

}
