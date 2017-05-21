import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	public static final double PARAMETER = 1.3;

	public static void main(String[] args) throws IOException {
		float num;

		// There were several ways of File handling ... so I did two kinds of
		// them. (그냥 두개의 방법으로 파일입출력 한거)
		ArrayList<Character> arrayList = new ArrayList<Character>();
		String filename = "저장데이터.txt";
		FileInputStream fis = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		while ((num = reader.read()) != -1) {
			if(num == '1' || num=='0')
				arrayList.add((char) num);
		}
		reader.close();
		fis.close();

		ArrayList<Character> arrayList2 = new ArrayList<>();
		FileReader reader1 = new FileReader("체크.txt");
		while ((num = reader1.read()) != -1) {
			if(num == '1' || num=='0')
			arrayList2.add((char) num);
		}
		reader1.close();

		// (parameter)% of User's beat should be chosen to n1_sag
		int n1[] = new int[arrayList.size()];
		int n2[] = new int[arrayList2.size()];
		int segmentedN1[] = new int[((int)(n2.length*PARAMETER))];
		int segSize = n1.length / ((int)(n2.length*PARAMETER));
		for (int i = 0; i < arrayList2.size(); i++) {
			n2[i] = arrayList2.get(i) - '0';
		}

		System.out.println("n2 길이 : " + n2.length + ", n1 : " + n1.length);
		// * parameter);

		int i = 0;
		double min=100;
		while (segSize > 0) {
			//System.out.println(segSize);
			for (int j = 0; j < ((int)(n2.length * PARAMETER)); j++) {
				segmentedN1[j] = arrayList.get(i) - '0';
				i++;
			}
			DTW dtw = new DTW(segmentedN1, n2);
			if(min>dtw.getDistance()){
				min = dtw.getDistance();
			}
			segSize--;
		}
		//skip the last binary numbers...
		System.out.println(min);

	}
}
