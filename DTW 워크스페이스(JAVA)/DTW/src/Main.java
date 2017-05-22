import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		float num;

		// There were several ways of File handling ... so I did two kinds of
		// them. (그냥 두개의 방법으로 파일입출력 한거)
		// arrayList에 저장
		ArrayList<Character> arrayList = new ArrayList<Character>();
		String filename = "저장데이터.txt";
		FileInputStream fis = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		while ((num = reader.read()) != -1) {
			if (num == '1' || num == '0')
				arrayList.add((char) num);
		}
		reader.close();
		fis.close();

		// arrayList2에 저장
		ArrayList<Character> arrayList2 = new ArrayList<>();
		FileReader reader1 = new FileReader("체크.txt");
		while ((num = reader1.read()) != -1) {
			if (num == '1' || num == '0')
				arrayList2.add((char) num);
		}
		reader1.close();

		// set arrayList to n1 & arrayList2 to n2
		float n1[] = new float[arrayList.size()];
		float n2[] = new float[arrayList2.size()];
		float segmentedN1[] = new float[((int) (n2.length))];
		int segSize = n1.length / ((int) (n2.length));
		for (int i = 0; i < arrayList2.size(); i++) {
			n2[i] = arrayList2.get(i) - '0';
		}
		System.out.println("n2 길이 : " + n2.length + ", n1 : " + n1.length);

		// compare each segmentedN1 with n1
		ArrayList<Integer> minArr = new ArrayList<>();
		int i = 0;
		double min = 100;
		int minSeg = 0;
		int tmpSegSize = segSize;
		while (tmpSegSize > 0) {
			for (int j = 0; j < ((int) (n2.length)); j++) {
				segmentedN1[j] = arrayList.get(i) - '0';
				i++;
			}
			DTW2 dtw = new DTW2(segmentedN1, n2);
			//finding 3 minSeg which makes minimum min value 
			if (min > dtw.getDistance()) {
				min = dtw.getDistance();
				minSeg = tmpSegSize;
				minArr.add(0, minSeg);
			}
			tmpSegSize--;
		}

		// go to minimum value stage!
		int tmpi;
		for (int l = 0; l < minArr.size(); l++) {
			int k = 0;
			i = (segSize - minArr.get(l) - 1) * n2.length + n2.length / 3;
			if (minArr.get(l) == segSize) { // 배열의 첫부분일 때 (minArr의 값은 왼쪽이 가장
				// 큰 segSize의 크기를 가짐)
				k = 2; // Do this 'for' 3 time!
				i = 0;
			} else if (minArr.get(l) == 1) {
				k = 2;
			}
			System.out.println(segSize + "," + minArr.get(l) + ", " + i);
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
			System.out.println(l + "번째 : " + min);
		}

		// skip the last binary numbers...

		
		
		
		
		
		
		
		/* This code is to practice the original DTW 
		 * 
		 * 
		 * 
		 * 
		 * ArrayList<Character> arrayList = new ArrayList<Character>(); String
		 * filename = "ww.txt"; FileInputStream fis = new
		 * FileInputStream(filename); BufferedReader reader = new
		 * BufferedReader(new InputStreamReader(fis)); while ((num =
		 * reader.read()) != -1) { if(num == '1' || num=='0')
		 * arrayList.add((char) num); } reader.close(); fis.close();
		 * 
		 * ArrayList<Character> arrayList2 = new ArrayList<>(); FileReader
		 * reader1 = new FileReader("ww2.txt"); while ((num = reader1.read()) !=
		 * -1) { if(num == '1' || num=='0') arrayList2.add((char) num); }
		 * reader1.close();
		 * 
		 * // (parameter)% of User's beat should be chosen to n1_sag float n1[]
		 * = new float[arrayList.size()]; float n2[] = new
		 * float[arrayList2.size()]; for (int i = 0; i < arrayList2.size(); i++)
		 * { n2[i] = arrayList2.get(i) - '0'; } for (int i = 0; i <
		 * arrayList.size(); i++) { n1[i] = arrayList.get(i) - '0'; }
		 * 
		 * DTW_2 dtw = new DTW_2(n1,n2); System.out.println(dtw.getDistance());
		 */
	}
}
