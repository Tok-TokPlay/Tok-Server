package com.example.dkdk6.toktokplay.Activity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Scanner;

public class ProcessJob implements Runnable{
	private boolean finished = false;
	private boolean checked = false;
	private double compareResult;
	
	private String fileName;
	private String userBeat;
	private String dbDirectory;
		
	public ProcessJob(String fileName, String userBeat, String dbDirectory)	{
		this.fileName = fileName;
		this.userBeat = userBeat;	
		this.dbDirectory = dbDirectory;
	}

	@Override
	public void run()	{
		// Run jar file with sub shell.	
		Runtime runTimeProcess = Runtime.getRuntime();
		String[] processJob = {"java","-jar", "ComparingJob.jar", dbDirectory, fileName, userBeat};
		try {
			Process process = new ProcessBuilder(processJob).start();
			/*
			 * If you want to see debugging tools...
			SequenceInputStream seqIn = new SequenceInputStream(process.getInputStream(), process.getErrorStream());	
			Scanner s = new Scanner(seqIn); 
			while (s.hasNextLine() == true) { 
				System.out.println(s.nextLine()); 
				}	
			*/
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
		return this.compareResult;
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
				try {
					resultReader = new FileReader(this.dbDirectory + "\\result\\"+ this.fileName.split(".txt")[0] + "resultFile.txt");
					// Save it to compareResult.
					this.compareResult = resultReader.read();
					resultReader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					// Do nothing but get error code.
				} catch (IOException e) {
					e.printStackTrace();
					// Do nothing but get error code.
				}
				// Check then delete it.
				resultFile.delete();
			return true;
		}
		else {
			return false;
		}
		
	}
}
