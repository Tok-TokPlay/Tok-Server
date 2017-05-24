import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Compare {

	// I will move the compare 'Main'class's function to here
	double compareResult(int fileNumber, float n2[]) throws IOException{
		double result=-1.0;
		int num;
		String filename =fileNumber +".txt";
		FileReader reader = new FileReader(filename);
		ArrayList<Character> arrayList = new ArrayList<Character>();
		
		
		while ((num = reader.read()) != -1) {
			// read only 1 and 0 (not '\n'...so on)
			if (num == '1' || num == '0')
				arrayList.add((char) num);
		}
		reader.close();
		
		float segmentedN1[] = new float[((int) (n2.length))];
		int segSize = arrayList.size() / n2.length;
		

		// compare each segmentedN1 with n1
		int i = 0;
		double min = 100;
		int minSeg = 0;
		int tmpSegSize = segSize;
		ArrayList<Integer> minSegArr = new ArrayList<>();
		while (tmpSegSize > 0) {
			for (int j = 0; j < ((int) (n2.length)); j++, i++) {
				segmentedN1[j] = arrayList.get(i) - '0';
			}
			DTW2 dtw = new DTW2(segmentedN1, n2);
			// finding any minSeg which makes minimum min value
			// I wanted to find 3 minSegs but I cannot focus on this right
			// now!!!!
			if (min > dtw.getDistance()) {
				min = dtw.getDistance();
				minSeg = tmpSegSize;
				minSegArr.add(0, minSeg);
			}
			tmpSegSize--;
		}
		
		// go to one of minimum value stage!
		int tmpi;
		for (int l = 0; l < minSegArr.size(); l++) {
			int k = 0;
			i = (segSize - minSegArr.get(l) - 1) * n2.length + n2.length / 3; // 3등분해서
																				// 지나감
			// Because the 'minSegArr == 1' means the last segment of the n1.
			if (minSegArr.get(l) == 1) {
				k = 2; // Do this 'for'loop just 3 time!
			} else if (minSegArr.get(l) == segSize) { // if the similarity part
														// is the first segment
														// of the n1.
				k = 2;
				i = 0;
			}
			// to see where is similarity part~
			for (; k < 5; k++) {
				tmpi = i;
				for (int j = 0; j < ((int) (n2.length)); j++, tmpi++) {
					segmentedN1[j] = arrayList.get(tmpi) - '0';
				}
				DTW2 dtw = new DTW2(segmentedN1, n2);
				if (min > dtw.getDistance()) {
					min = dtw.getDistance();
				}
				i = i + n2.length / 3;
			}
		}
		result = min;
		return result;
	}
}
