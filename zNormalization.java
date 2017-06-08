double[] zNormalization(double[] T) { 
	double[] result = new double[T.length];
	double sum = 0;
	
	for(int i = 0; i < T.length; i++) {
		sum = sum + T[i];
	}
	double mean = sum / T.length;
	
	sum = 0;
	for(int i = 0; i < T.length; i++) {
		sum = sum + (T[i] - mean) * (T[i] - mean);
	}
	double std = sum / T.length;
	std = Math.sqrt(std);

	for(int i = 0; i < T.length; i++) {
		result[i] = (T[i] - mean) / std;
	}

	return result;
}
