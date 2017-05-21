
public class DTW {

	private double distance;
	private static int MAX = 99999;
	public DTW(int[] n1,int[] n2){
		int i,j = 0,cost =0;
		int dtw[][]=new int[n1.length+1][n2.length+1];
		dtw[0][0]=0;
		//because n2 has small size,
		for(i=1;i<=n2.length;i++){
			dtw[i][0]=MAX;
			dtw[0][i]=MAX;
		}
		for(;i<=n1.length;i++){
			dtw[i][0]=MAX;
		}
		
		for(i=1;i<=n1.length;i++){
			for(j=1;j<=n2.length;j++){
				cost = (int) Math.abs(n1[i-1]-n2[j-1]);
				dtw[i][j] = cost+Math.min(dtw[i-1][j-1],Math.min(dtw[i-1][j],dtw[i][j-1]));
			}
		}
		distance = (double)dtw[n1.length][n2.length]/n2.length;
	}
	public double getDistance(){
		return distance;
	}
}
