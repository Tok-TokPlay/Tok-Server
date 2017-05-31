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
	double compareResult(int fileNumber, int n2[]) throws IOException {
		double result = -1.0;
		int num;
		StringTokenizer token;
		String nums=null;
		String filename = "C:\\Users\\caucse\\Desktop\\SongData"+fileNumber + ".txt";
		FileReader reader = new FileReader(filename);
		ArrayList<Character> arrayList = new ArrayList<Character>();
		
		try {
			   BufferedReader in  = new BufferedReader(new FileReader(filename)); //파일로부터 데이터를 읽어 버퍼에 저장
			   
			   String temp;
			   
			   while ((temp = in.readLine()) != null) { //한 줄씩 읽기
			    token = new StringTokenizer(temp, " ");  //공백 단위로 끊기 
			    for (int i = 0; token.hasMoreTokens(); i++) { //한 줄에 있는 단어 수만큼 
			     arrayList.add(token.nextToken()); //복사하여 쓰기 
			    }
			    out.write("\n"); //줄바꿈 표시 
			   }
			   in.close();  //파일 스트림 닫기
			   out.close(); //파일 스트림 닫기 
			  } catch (Exception e){
			   e.printStackTrace();
			  } 

			출처: http://commonintelligence.tistory.com/entry/자바-파일-입출력 [잡 지식의 세계]
		while ((num = reader.read()) != -1) {
			nums=null;
			nums+=num;
			while((num = reader.read()) != -1 && num != ','){
				nums+=num;
			}
			// read only 1 and 0 (not '\n'...so on)
			if (num  == '1' || num  == '0'|| num  == '2'|| num  == '3'|| num  == '4'|| num  == '5'|| num  == '6'|| num  == '7'|| num  == '8'|| num  == '9')
				arrayList.add((char) num);
		}
		reader.close();

		int segmentedN1[] = new int[SEG_LENGTH];
		int segmentedN1_1000[] = new int[SEG_LENGTH_1000];
		int segSize = arrayList.size() / SEG_LENGTH_1000;

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
					segmentedN1[j] = arrayList.get(i) - '0';
				}
			else {
				for (int j = 0; j < SEG_LENGTH_1000; j++, i++) {
					segmentedN1_1000[j] = arrayList.get(i) - '0';
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
					segmentedN1_1000[j] = arrayList.get(tmpi) - '0';
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
