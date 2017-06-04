/**
 * This class implements the Dynamic Time Warping algorithm given two sequences.
 * 	input : two sequences
 *  	X = x1, x2, ..., xi, ..., xn
 *  	Y = y1, y2, ..., yj, ..., ym
 * 	output : similarity of given two sequences.
 */

public class DTW2 {
	private int[] seq1;
	private int[] seq2;
	private int n, m, K;
	private double warpingDistance;

	public DTW2(int[] sample, int[] templete) {
		// Initialize DTW Values with given parameter.
		seq1 = sample;
		seq2 = templete;

		n = seq1.length;
		m = seq2.length;
		K = 1;
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
	private double distanceBetween(double p1, double p2) {
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
}
