/**
 * This class implements the Dynamic Time Warping algorithm given two sequences.
 * 	input : two sequences
 *  	X = x1, x2, ..., xi, ..., xn
 *  	Y = y1, y2, ..., yj, ..., ym
 * 	output : similarity of given two sequences.
 */

public class DTW2 {
	// Algorithms basic data structure.
	private int[] seq1;
	private int[] seq2;
	private double[][] localDistance;
	private double[][] globalDistance;
	
	public DTW2(int[] sample, int[] templete) {
		// Initialize DTW Values with given parameter.
		this.seq1 = sample;
		this.seq2 = templete;
		
		// local and global distance array to calculate DTW.
		localDistance = new double[getSeq1().length][getSeq2().length];
		globalDistance = new double[getSeq1().length][getSeq2().length];
	}

	public double getInverseSimilarity()	{
		// Calculate inversed similarity.
		// If the result is large, two input sequence is not similar.
		// local variable accumulatedDistance, localDistance, globalDistance
		// accumulatedDistance : Now Calculating distance from [0][0] to [MAX_LEGNTH1][MAX_LENGTH2}
		double accumulatedDistance = 0.0;
		int K = 1;
		
		for (int i = 0; i < getSeq1().length; i++) {
			for (int j = 0; j < getSeq2().length; j++) {
				// Check all local distance with two sequence ( with Euclidean distance checking. )
				localDistance[i][j] = distanceBetween(seq1[i], seq2[j]);
			}
		}
		
		// First ( 0, 0 ) global distance is loca distance( 0, 0 ).
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
				accumulatedDistance = Math.min(Math.min(globalDistance[row - 1][column], globalDistance[row - 1][column - 1]), globalDistance[row][column - 1]);
				accumulatedDistance += localDistance[row][column];
				globalDistance[row][column] = accumulatedDistance;	
			}	
		}
		
		accumulatedDistance = globalDistance[getSeq1().length - 1][getSeq2().length - 1];
		int i = getSeq1().length - 1;
		int j = getSeq2().length - 1;
		int minIndex = 1;
		while ((i + j) != 0) {
			if (i == 0) {
				j -= 1;
			}
			else if (j == 0) {
				i -= 1;
			}
			else { // i != 0 && j != 0
				double[] array = { globalDistance[i - 1][j], globalDistance[i][j - 1], globalDistance[i - 1][j - 1] };
				minIndex = this.getIndexOfMinimum(array);
				if (minIndex == 0) {
					i -= 1;
				} else if (minIndex == 1) {
					j -= 1;	
				} 
				else if (minIndex == 2) {
					i -= 1;
					j -= 1;	
				}
			} // end else
			K++;
		} // end while	
		return accumulatedDistance / K;
	}
	private double distanceBetween(double p1, double p2) {
		// Return Euclidean Distance between two point.
		return Math.sqrt((p1 - p2) * (p1 - p2));
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
