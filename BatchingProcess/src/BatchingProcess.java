import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BatchingProcess {
	public static void main(String[] args) throws InterruptedException, IOException{
		
		String command ="C:\\Users\\caucse\\Desktop\\batFile\\test.bat";
		Process proc = Runtime.getRuntime().exec(command);
		InputStreamReader isr = new InputStreamReader(proc.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line=null;
		while((line=br.readLine())!=null){
			System.out.println(line+"\n");
		}
		int waitFor=proc.waitFor();
		int result=proc.exitValue();
		System.out.println(result+"");
	}

}
