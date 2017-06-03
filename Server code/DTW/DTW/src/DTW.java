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
				// sequence's order is 1 smaller then dtw map.
				
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
