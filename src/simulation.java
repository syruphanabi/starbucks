
public class simulation {
	public static void main(String args[]) {
		
		double simTime = 36000;
		double lambda = 0.056;
		double deltaT = 10;
		
		double OrderTime = 60;
		double CakeDeliTime = 25;
		double ColdFillTime = 45;
		double HotFillTime = 70;
		double PickUpTime = 30;
		double HeatTime = 150;
		
		double[] engineInfo = {OrderTime, CakeDeliTime, ColdFillTime, 
		                     HotFillTime, PickUpTime, HeatTime};
		
		stat cse6730 = new stat();
		
		//single run
		starbucks clough_0 = new starbucks(simTime, lambda, deltaT, engineInfo, cse6730);
		clough_0.run();
		
		
		//waiting time simulation
		cse6730.init();
		for (int i = 0; i < 500; i++) {
			starbucks clough = new starbucks(1800, 0.20, deltaT, engineInfo, cse6730);
			System.out.println("\n\nSimulation No. " + (i+1));
			clough.run();	
		}
		System.out.println("\n\nwaitTime - numWaitPeople");
		cse6730.printWaitTime();
		
		
		//lambda - max wait time
		double table[][] = new double[15][2];
		for (int j = 0; j < 15; j++) {
			table[j][0] = 0;
			table[j][1] = 0;
		}
		int count = 0;
		for (int l = 1; l < 16; l++) {
			lambda = l * 0.02;
			cse6730.init();
			for(int k = 0; k < 200; k++) {
				starbucks clough = new starbucks(7200, lambda, deltaT, engineInfo, cse6730);
				System.out.println("\n\nlambda = " + lambda);
				clough.run();
			}
			table[count][0] = lambda;
			table[count][1] = cse6730.maxRecord;
			count ++;
		}
		System.out.println("\n\nlambda - max wait time");
		for (int j = 0; j < 15; j++) {
			System.out.println(table[j][0] + "\t" + table[j][1]);
		}
		
	}
}
