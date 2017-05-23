public class DTW {
	private double distance;
	private static int MAX = 99999;
	/*
		n1 is horizontal sequence.
		n2 is vertical sequence.
		
		Calculate DTW with only "Uclidean Distance".
	*/
	public DTW(int[] n1,int[] n2){
		int i,j = 0;
		int cost = 0;
		// Initialize must doing one statement by one line.

		int dtw[][] = new int[n1.length + 1][n2.length + 1];
		// dtw[][] will be used to show weigt of each path.
		
		// dtw array vertical and horizontal initialize start.
		
		dtw[0][0] = Math.abs(n1[0] - n2[0]);
		//(0, 0) must be initialize with Uclidean distance.

		for(i = 1; i <= n2.length; i++)	{
			dtw[0][i] = dtw[0][i-1] + Math.abs(n1[i-1] - n2[0]);
			// Initialize (0, i) to each distance, not MAX value.
		}
		
		for(i = 1; i <= n1.length; i++)	{
			dtw[i][0] = dtw[i-1][0] + Math.abs(n1[0] - n2[i]);
			// Initialize (i, 0) to each distance, not MAX value.
		}
		
		// finish initialize.
		
		for(i = 1 ; i <= n1.length ; i++){
			for(j = 1 ; j <= n2.length ; j++){
				// Start from (1, 1), take min value of dtw[i-1][j], dtw[i][j-1], dtw[i-1][j-1] + cost.
				// Cost will be "Uclidean Distance" of each sequence.
				cost = (int)Math.abs(n1[i-1] - n2[j-1]);
				dtw[i][j] = cost + Math.min(dtw[i-1][j-1], Math.min(dtw[i-1][j], dtw[i][j-1]));
			}
		}
		
		distance = (double)dtw[n1.length][n2.length]/n2.length;
	}
	
	public double getDistance()	{
		return this.distance;
		// to clarify which variable must be return, add this.
	}
	
	public void showSequence(int dtw[][] )	{
		// print dtw map to console.
		for(int i = 0; i < dtw.length; i++)	{
			for(int j = 0; j < dtw[i].length; j++)	{
				System.out.print(dtw[i][j] + "\t");
			}
			System.out.print("\n");
		}
	}

	public void showSequence(int n1[], int n2[])	{
		// Easy function to print out n1 and n2.
		
		System.out.println("n1 sequence : ");
		for(int i = 0; i < n1.length; i++)	{
			System.out.print(n1[i] + "\t");
		}
		System.out.println("n2 sequence : ");
		for(int i = 0; i < n1.length; i++)	{
			System.out.print(n2[i] + "\t");
		}
	}
}

/**
 * Created by park jin hee on 2017-05-19.
 */


/**
 	 * This class implements the Dynamic Time Warping algorithm
 	 * given two sequences
 	 * <pre>
	 * 	input : two sequences
 	 *  	X = x1, x2, ..., xi, ..., xn
 	 *  	Y = y1, y2, ..., yj, ..., ym
	 * 	output : similarity of given two sequences.
 	 *  </pre>
 	 *

 	 * @version     1.0
 	 */

class DTW2 {

    protected float[] seq1;
    protected float[] seq2;
    protected int[][] warpingPath;
    protected int n;
    protected int m;
    protected int K;

    protected double warpingDistance;

    public DTW2(float[] sample, float[] templete) {
        seq1 = sample;
        seq2 = templete;

        n = seq1.length;
        m = seq2.length;
        K = 1;

        warpingPath = new int[n + m][2]; // max(n, m) <= K < n + m
        warpingDistance = 0.0;

        this.compute();
    }

    public void compute() {
        double accumulatedDistance = 0.0;

        double[][] d = new double[n][m]; // local distances
        double[][] D = new double[n][m]; // global distances

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = distanceBetween(seq1[i], seq2[j]);
            }
        }

        D[0][0] = d[0][0];

        for (int i = 1; i < n; i++) {
            D[i][0] = d[i][0] + D[i - 1][0];
        }

        for (int j = 1; j < m; j++) {
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

        warpingPath[K - 1][0] = i;
        warpingPath[K - 1][1] = j;

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
            warpingPath[K - 1][0] = i;
            warpingPath[K - 1][1] = j;
        } // end while
        warpingDistance = accumulatedDistance / K;

        this.reversePath(warpingPath);
    }

    /**
     * Changes the order of the warping path (increasing order)
     *
     * @param path
     *            the warping path in reverse order
     */
    protected void reversePath(int[][] path) {
        int[][] newPath = new int[K][2];
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < 2; j++) {
                newPath[i][j] = path[K - i - 1][j];
            }
        }
        warpingPath = newPath;
    }

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
    public String toString() {
        String retVal = "Warping Distance: " + warpingDistance + "\n";
        retVal += "Warping Path: {";
        for (int i = 0; i < K; i++) {
            retVal += "(" + warpingPath[i][0] + ", " + warpingPath[i][1] + ")";
            retVal += (i == K - 1) ? "}" : ", ";

        }
        return retVal;
    }

}
