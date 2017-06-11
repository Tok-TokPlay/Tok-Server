package com.example.dkdk6.toktokplay.Activity;
import java.util.ArrayList;

public class MultiProcessing{
	private ArrayList<Double> resultQueue;
	private ArrayList<String> fileNameQueue;
	private int nowProcessingNumber;
	private int nextProcessNumber;
	private int destProcessNumber;
	private ProcessJob[] jobList;
	
	public MultiProcessing(ProcessJob[] job)	{
		// Default dest process number is 10;
		this(job,  10);
	}
	
	public MultiProcessing(ProcessJob[] job, int destProcessNumber)	{
		// Initialize inner variable.
		this.nowProcessingNumber = 0;
		this.nextProcessNumber = 0;

		this.resultQueue = new ArrayList<Double>();
		this.fileNameQueue = new ArrayList<String>();	
		this.destProcessNumber = destProcessNumber;
		this.jobList = job;
	}
	
	public void multiProcessStart()	{
		// Make Thread list for jobList.
		Thread[] threadList = new Thread[getProcessNumber()];
		for(int i = 0; i < jobList.length; i++) {
			threadList[i] = new Thread(jobList[i]);
		}

		// Multiprocessing start.
		int checkTime = 0;
		while(isWorkDone() == false)	{
			if(checkTime % 1000 == 0)	{
				// Check at only 1000 time per 1.
				checkTime = 1;
				checkFinished();
			}
			processStart(threadList);
			checkTime++;
		}
		// Check if all process is finished.
		checkFinished();
	}

	private void processStart(Thread[] threadList)	{
		while(nowProcessingNumber < destProcessNumber && nextProcessNumber < this.getProcessNumber())	{
			// if can processable and now processing program`s number is lower than destProcessNumber...
			System.out.println("[Job Started]" + nextProcessNumber + " th job : " + jobList[nextProcessNumber].getFileName());
			threadList[nextProcessNumber].run();
			// nextProcessNumber is larger because we have to do next job.
			// nowProcessingNumber is larger because process is larger.
			nowProcessingNumber = nowProcessingNumber + 1;
			nextProcessNumber = nextProcessNumber + 1;
		}
	}
	
	public String getMusicKey()	{
		double resultValue = 1000000;
		int resultIndex = 0;
		// As big value to check smallest value.
		for(int i = 0; i < getProcessNumber(); i++)	{
			// Find smallest value of resultQueue.
			if(resultValue > resultQueue.get(i)){ 
				resultIndex = i;
				resultValue = resultQueue.get(i);
			}
		}
		// Return smallest value`s file name.
		return this.fileNameQueue.get(resultIndex);
	}
	
	private void checkFinished()	{
		// Check if process is finished, and not checked, check result and set checked value true.
		for(int i = 0; i < getProcessNumber(); i++)	{
			jobList[i].checkFinish();
			if(jobList[i].isChecked() == false)	{
				if(jobList[i].isFinished()) {
					jobList[i].setChecked(true);
					System.out.println("[Job Finished]" + i + " th job : " + jobList[i].getFileName());
					
					// add result to resultQueue and fileNameQueue.
					resultQueue.add(jobList[i].getCompareResult());
					fileNameQueue.add(jobList[i].getFileName());
					// minus nowProcessingNumber 1 let know process is finished and can do more job.
					nowProcessingNumber = nowProcessingNumber - 1;
				}
			}
		}
	}
	
	private int getNowProcessedNumber()	{
		// How many process is finished.
		return this.resultQueue.size();
	}
	
	private int getProcessNumber()	{
		// How many job is in list.
		return this.jobList.length;
	}
	
	private boolean isWorkDone()	{
		// if work is done, which mean processedNumber is same as processNumber...
		if(getNowProcessedNumber() == getProcessNumber()) {
			return true;
		}
		else {
			return false;
		}
	}
}
