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
