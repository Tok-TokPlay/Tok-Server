import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Compare {

	private final static int SEG_LENGTH_1000 = 1000;	//segment the N1(original array) with size 1000
	public final static int SEG_LENGTH = SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3);
	private final static int MIN_ERROR_RANGE = 5;
	private int correctable[] = new int[SEG_LENGTH];

	// I will move the compare 'Main'class's function to here
	double compare(int fileNumber, ArrayList<Integer> n2ArrayList) throws IOException {
		double result = -1.0;
		int num;
		StringTokenizer token;
		String nums=null;
		String filename = "C:\\Users\\caucse\\Desktop\\SongData"+fileNumber + ".txt";
		FileReader reader = new FileReader(filename);
		ArrayList<String> arrayList = new ArrayList<>();
		
			   BufferedReader in  = new BufferedReader(new FileReader(filename)); //파일로부터 데이터를 읽어 버퍼에 저장
			   
			   String temp;
			   
			   while ((temp = in.readLine()) != null) { //한 줄씩 읽기
			    token = new StringTokenizer(temp, ",");  //공백 단위로 끊기 
			    for (int i = 0; token.hasMoreTokens(); i++) { //한 줄에 있는 단어 수만큼 
			     arrayList.add(token.nextToken()); //복사하여 쓰기 
			     System.out.println(arrayList.get(i));
			    }
			   }
			   in.close();  //파일 스트림 닫기

		int segmentedN1[] = new int[SEG_LENGTH];
		int segmentedN1_1000[] = new int[SEG_LENGTH_1000];
		int segSize = arrayList.size() / SEG_LENGTH_1000;

		// compare each segmentedN1 with n1
		int i = 0;
		double distance;
		int minSeg = 0;
		int SegLocation = segSize;
		int arrLength = 0;
		int[] n2 = new int[n2ArrayList.size()];
		double[] min = new double[MIN_ERROR_RANGE];
		int[] minSegArr = new int[MIN_ERROR_RANGE];

		while (SegLocation > 0) {
			// if it is the last segment of the arrayList, the last length would
			// be smaller than SEG_LENGTH
			if (SegLocation != 1)
				for (int j = 0; j < SEG_LENGTH; j++, i++) {
					segmentedN1[j] = Integer.parseInt(arrayList.get(i));
				}
			else {
				for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
					segmentedN1_1000[j] = Integer.parseInt(arrayList.get(i));
				}
			}
			// to store again (about seg_length/3 size) at next turn...
			i -= SEG_LENGTH_1000 / 3;

			//n2ArrayList to n2
			for(int j=0;i<n2ArrayList.size();i++)
				n2[j]=(int) n2ArrayList.get(j);
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
		double min2 = 100;
		for (int l = 0; l < MIN_ERROR_RANGE; l++) {
			int k = 0;
			i = (segSize - minSegArr[l] - 1) * SEG_LENGTH_1000 + (SEG_LENGTH_1000 / 3); // 3등분해서
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
				//temporary, I set tmpi boundary
				for (int j = 0; j < SEG_LENGTH_1000 &&tmpi<200000; j++, tmpi++) {
					segmentedN1_1000[j] = Integer.parseInt(arrayList.get(tmpi));
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

		arrayList.clear();
		result = min2;
		return result;
	}

	int[] getCorrectable() {
		return correctable;
	}
}
