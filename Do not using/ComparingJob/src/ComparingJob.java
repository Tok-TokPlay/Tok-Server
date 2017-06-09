import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ComparingJob {
	public static void main(String[] argc)	{
		double smallestValue = -1;
		// Compare user beat and fileName.
		// argc[0] == filePath ( C:\\$DB_DIRECTORY... )
		// argc[1] == fileName ( musicName.txt )
		// argc[2] == String userBeat 0001111111000000011...
		if(argc.length != 3)	{
		}
		else {
			ComparingWithFastDTW comparing = null;
			try {
				comparing = new ComparingWithFastDTW(argc[0], argc[2]);
				smallestValue = comparing.compareFile(argc[1], argc[2]);
				File resultFile = new File(argc[0] + "\\" + argc[1].split(".txt")[0] + "resultFile.txt");
				
				resultFile.delete();
				// delete exist file.
				resultFile.createNewFile();
				// Create new one.
				
				FileWriter resultWriter = new FileWriter(resultFile, true) ;
				
				// Write value and finish act.
				resultWriter.write(String.valueOf(smallestValue));
				resultWriter.flush();
				resultWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				// Do nothing but get error code.
			}
		}
	}
}