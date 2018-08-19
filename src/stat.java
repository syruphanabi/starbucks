
public class stat {
	double[][] waitPersonTime;
	int maxRecord = -1;
	
	public stat() {
		waitPersonTime = new double[1000][2];
		for(int i = 0; i < 1000; i++) {
			waitPersonTime[i][0] = 0.0;
			waitPersonTime[i][1] = 0.0;
		}
	}
	
	public void init() {
		for(int i = 0; i < 1000; i++) {
			waitPersonTime[i][0] = 0.0;
			waitPersonTime[i][1] = 0.0;
		}
		maxRecord = -1;
	}
	
	public void record(int people, double time) {
		if (people > maxRecord) {
			maxRecord = people;
		}
		waitPersonTime[people][0] = (waitPersonTime[people][0] * waitPersonTime[people][1] + time) 
				/ (waitPersonTime[people][1] + 1);
		waitPersonTime[people][1]++;
	}
	
	
	
	public void printWaitTime() {
		
		for(int j = 0; j < maxRecord; j++) {
			System.out.println(j + "\t" + waitPersonTime[j][0] + "\t" + waitPersonTime[j][1]);
		}
		
	}
}
