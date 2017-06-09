import java.io.File;

public class Main {
	public static void main(String[] argc)	{
		String dbDirectory = "D:\\Workspace\\TokTok\\Tok-Server\\Server code\\TXT";
		File dbDirectoryPath = new File(dbDirectory);
		String userBeat = "010100000001111111110000000000000000000000000000000111110110110000000011111111111111111010100110111";
		File[] fileList = dbDirectoryPath.listFiles();
		ProcessJob[] jobList = new ProcessJob[fileList.length];

		for(int i = 0; i < fileList.length; i++)	{
			jobList[i] = new ProcessJob(fileList[i].getName(),userBeat,dbDirectory);
		}
		
		MultiProcessing processor = new MultiProcessing(jobList, 20);
		processor.multiProcessStart();
		System.out.println(processor.getMusicKey());
	}
}
