import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ComparingJob {
	public static void main(String[] argc)	{
		double smallestValue = -1;
		// Compare user beat and fileName.
		// argc[0] == filePath ( C:\\$DB_DIRECTORY... )
		// argc[1] == fileName ( musicName.txt )
		// argc[2] == String userBeat 0001111111000000011... -> Changed userBeat file Name.
		if(argc.length != 3)	{
		}
		else {
			ComparingWithFastDTW comparing = null;
			// Read User beat with given file name. ( which is absolute file dest. )
			try {
				String userBeat = "";
				
				File userBeatFile = new File(argc[2]);
				if (userBeatFile.exists())	{
					FileReader beatReader;
					BufferedReader lineReader;
					try {
						beatReader = new FileReader(argc[2]);
						lineReader = new BufferedReader(beatReader);
						
						userBeat = lineReader.readLine();
						// Save it to compareResult.
						beatReader.close();
						lineReader.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						// Do nothing but get error code.
					} catch (IOException e) {
						e.printStackTrace();
						// Do nothing but get error code.
					}
				}
				
				comparing = new ComparingWithFastDTW(argc[0], userBeat);
				smallestValue = comparing.compareFile(argc[1], userBeat);
				
				File resultFile = new File(argc[0] + "\\result\\" + argc[1].split(".txt")[0] + "resultFile.txt");
				File resultDir = new File(argc[0] + "\\result\\");
				
				if(resultDir.exists() == false)	{
					resultDir.mkdirs();
				}
				if(resultFile.exists())	{
					// delete exist file.
					resultFile.delete();
				}
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