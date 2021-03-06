package com.example.dkdk6.toktokplay.Activity;
/*
public class DTW2 {
	// Algorithms basic data structure.
	private int[] seq1;
	private int[] seq2;
	private double[][] localDistance;
	private double[][] globalDistance;
	
	private boolean calculated = false;
	
	public DTW2(int[] sample, int[] templete) {
		// Initialize DTW Values with given parameter.
		this.seq1 = sample;
		this.seq2 = templete;
		
		// local and global distance array to calculate DTW.
		localDistance = new double[getSeq1().length][getSeq2().length];
		globalDistance = new double[getSeq1().length][getSeq2().length];
		
		fillDistance();
	}
	
	private void fillDistance()	{
		// Calculate inversed similarity.
		// If the result is large, two input sequence is not similar.
		// local variable accumulatedDistance, localDistance, globalDistance
		// accumulatedDistance : Now Calculating distance from [0][0] to [MAX_LEGNTH1][MAX_LENGTH2}
		double accumulatedDistance = 0.0;
		// Init Calculated value.
		this.calculated = true;
		
		for (int i = 0; i < getSeq1().length; i++) {
			for (int j = 0; j < getSeq2().length; j++) {
				// Check all local distance with two sequence ( with Euclidean distance checking. )
				localDistance[i][j] = distanceBetween(seq1[i], seq2[j]);
			}
		}
				
		// First ( 0, 0 ) global distance is local distance( 0, 0 ).
		// Initializing it.
		globalDistance[0][0] = localDistance[0][0];
		
		for (int row = 1; row < seq1.length; row++) {
			// Initializing first row of global distance with local distance.
			globalDistance[row][0] = localDistance[row][0] + globalDistance[row - 1][0];
		}
		
		for (int column = 1; column < seq2.length; column++) {
			// Initializing first column of global distance with local distance.
			globalDistance[0][column] = localDistance[0][column] + globalDistance[0][column - 1];
		}
			
		for (int row = 1; row < getSeq1().length; row++) {
			for (int column = 1; column < getSeq2().length; column++) {
				// Initialize distance table ( Global Distance ) with Euclidean Distance.
				// Gathering smallest values in adjacent indexes.
				accumulatedDistance = Math.min(Math.min(globalDistance[row - 1][column], globalDistance[row - 1][column - 1]), globalDistance[row][column - 1]);
				
				// Add before and now distance to Global distance.
				accumulatedDistance = accumulatedDistance + localDistance[row][column];
				globalDistance[row][column] = accumulatedDistance;	
			}	
		}
		
		// Max value is at [-1][-1].
		accumulatedDistance = globalDistance[getSeq1().length - 1][getSeq2().length - 1];
	}

	public double getInverseSimilarity()	{
		// Return Calculated distance. if not calculated, return false.
		if(this.calculated) {
			return this.globalDistance[getSeq1().length - 1][getSeq2().length - 1];
		}
		else {
			return -1;
		}
	}
	
	private double distanceBetween(double p1, double p2) {
		// Return Euclidean Distance between two point.
		return Math.sqrt((p1 - p2) * (p1 - p2));
	}

	// Doesn`t need to make setter because this class just need for compute each seq1, seq2 at constructor.
	public int[] getSeq1(){
		return this.seq1;
	}
	
	public int[] getSeq2(){
		return this.seq2;
	}
	
	public double[][] getLocalDistance()	{
		return this.localDistance;
	}
	
	public double[][] getGlobalDistance()	{
		return this.globalDistance;
	}
}
*/
/**
 * Created by park jin hee on 2017-05-19.
 */

/**
 * This class implements the Dynamic Time Warping algorithm given two sequences
 * 
 * <pre>
* 	input : two sequences
*  	X = x1, x2, ..., xi, ..., xn
*  	Y = y1, y2, ..., yj, ..., ym
* 	output : similarity of given two sequences.
 * </pre>
 *
 * 
 * @version 1.1
 */
public class DTW2 {
	protected int[] seq1;
	protected int[] seq2;
	protected int n, m, K;
	protected double warpingDistance;

	public DTW2(int[] sample, int[] templete) {
		seq1 = sample;
		seq2 = templete;

		n = seq1.length;
		m = seq2.length;
		K = 1;
//System.out.println("n ,m : "+n+","+m);
		// max(seq1.length, seq2.length) <= K < seq1.length + seq2.length
		warpingDistance = 0.0;

		System.gc();
		this.compute();
		System.gc();
	}

	public void compute() {
		System.gc();
		double accumulatedDistance = 0.0;
		System.gc();
		double[][] d = new double[n][m]; // local distances
		double[][] D = new double[n][m]; // global distances

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				d[i][j] = distanceBetween(seq1[i], seq2[j]);
			}
		}

		D[0][0] = d[0][0];

		for (int i = 1; i < seq1.length; i++) {
			D[i][0] = d[i][0] + D[i - 1][0];
		}

		for (int j = 1; j < seq2.length; j++) {
			D[0][j] = d[0][j] + D[0][j - 1];
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				accumulatedDistance = Math.min(Math.min(D[i - 1][j], D[i - 1][j - 1]), D[i][j - 1]);
				accumulatedDistance += d[i][j];
				D[i][j] = accumulatedDistance;
			}
		}
		accumulatedDistance = D[n - 1][m - 1];

		int i = n - 1;
		int j = m - 1;
		int minIndex = 1;

		while ((i + j) != 0) {
			if (i == 0) {
				j -= 1;
			} else if (j == 0) {
				i -= 1;
			} else { // i != 0 && j != 0
				double[] array = { D[i - 1][j], D[i][j - 1], D[i - 1][j - 1] };
				minIndex = this.getIndexOfMinimum(array);

				if (minIndex == 0) {
					i -= 1;
				} else if (minIndex == 1) {
					j -= 1;
				} else if (minIndex == 2) {
					i -= 1;
					j -= 1;
				}
			} // end else
			K++;
		} // end while
		warpingDistance = accumulatedDistance / K;

	}

	/**
	 * Changes the order of the warping path (increasing order)
	 *
	 * @param path
	 *            the warping path in reverse order
	 */

	/**
	 * Returns the warping distance
	 *
	 * @return
	 */
	public double getDistance() {
		return warpingDistance;
	}

	/**
	 * Computes a distance between two points
	 *
	 * @param p1
	 *            the point 1
	 * @param p2
	 *            the point 2
	 * @return the distance between two points
	 */
	protected double distanceBetween(double p1, double p2) {
		return (p1 - p2) * (p1 - p2);
	}

	/**
	 * Finds the index of the minimum element from the given array
	 *
	 * @param array
	 *            the array containing numeric values
	 * @return the min value among elements
	 */
	protected int getIndexOfMinimum(double[] array) {
		int index = 0;
		double val = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i] < val) {
				val = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Returns a string that displays the warping distance and path
	 */
}
