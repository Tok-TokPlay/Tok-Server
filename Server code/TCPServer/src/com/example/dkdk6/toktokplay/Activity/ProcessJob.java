package com.example.dkdk6.toktokplay.Activity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Scanner;

public class ProcessJob implements Runnable{
	private boolean finished = false;
	private boolean checked = false;
	private String compareResult;
	
	private String fileName;
	private String userBeatName;
	private String dbDirectory;
		
	public ProcessJob(String fileName, String userBeatName, String dbDirectory)	{
		this.fileName = fileName;
		this.userBeatName = userBeatName;	
		this.dbDirectory = dbDirectory;
	}

	@Override
	public void run()	{
		// Run jar file with sub shell.	
		String[] processJob = {"java","-jar", "ComparingJob.jar", dbDirectory, fileName, userBeatName};
		try {
			Process process = new ProcessBuilder(processJob).start();
			
			// If you want to see debugging tools...
			SequenceInputStream seqIn = new SequenceInputStream(process.getInputStream(), process.getErrorStream());	
			Scanner s = new Scanner(seqIn); 
			while (s.hasNextLine() == true) { 
				System.out.println(s.nextLine()); 
				}	
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkFinish()	{
		// Get Result of comparing.		
		if(readCompareResult() == true)	{
			finished = true;
		}
	}
	public boolean isFinished()	{
		return this.finished;
	}

	public double getCompareResult()	{
		return Double.valueOf(this.compareResult);
	}

	public void setChecked(boolean checked)	{
		this.checked = checked;
	}

	public boolean isChecked()	{
		return this.checked;
	}
	
	public String getFileName()	{
		return this.fileName;
	}
	
	private boolean readCompareResult()	{
		// read fileName.split(".txt")[0] + resultFile + .txt and get CompareResult.
		File resultFile = new File(this.dbDirectory + "\\result\\"+ this.fileName.split(".txt")[0] + "resultFile.txt");
			if (resultFile.exists())	{
				FileReader resultReader;
				BufferedReader lineReader;
				try {
					resultReader = new FileReader(this.dbDirectory + "\\result\\"+ this.fileName.split(".txt")[0] + "resultFile.txt");
					lineReader = new BufferedReader(resultReader);
					// Save it to compareResult.
					this.compareResult = lineReader.readLine();
					resultReader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					// Do nothing but get error code.
				} catch (IOException e) {
					e.printStackTrace();
					// Do nothing but get error code.
				}
				resultFile.delete();
				// Check then delete it.
			return true;
		}
		else {
			return false;
		}
		
	}
}
