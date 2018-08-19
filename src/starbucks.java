
public class starbucks {
	
//	double simTime = 36000;
//	double lambda = 0.056;
//	double deltaT = 10;
	double simTime;
	double lambda;
	double deltaT;
	double[] engineInfo;
	stat cse6730;
	
//	public starbucks() {
//		;
//	}
	
	public starbucks(double simTime, double lambda, double deltaT, double[] engineInfo, stat stat) {
		this.simTime = simTime;
		this.lambda = lambda;
		this.deltaT = deltaT;
		this.engineInfo = engineInfo;
		this.cse6730 = stat;
	}
	
	public void run() {
		
		//infrastructure
		Engine engine = new Engine(engineInfo, cse6730);
		
		//input generator
		double geneTime = 0;
		int geneIndex = 0;
		Customer[] CustomerRecord = new Customer[(int)(simTime/deltaT * lambda * 5)]; //temp
		
		while (geneTime < simTime) {
			
			geneTime += deltaT;
			double show = Math.random();
			if (show > lambda) {
				continue;
			}
			
			String Type;
			double rand = Math.random();
			if (rand < 0.1) {
				Type = "cake";
			}else if (rand < 0.2) {
				Type = "sandwich";
			}else if (rand < 0.6) {
				Type = "hot";
			}else {
				Type = "cold";
			}
			
			CustomerRecord[geneIndex] = new Customer(geneIndex, geneTime,Type);
			engine.FEL.addEvent(new Event(geneTime, CustomerRecord[geneIndex], "arrival"));
			geneIndex += 1;
		}
		
		Event e = engine.FEL.getFirstEvent();
		while (e != null) {
			System.out.println(String.valueOf(e.cust.index) + " " + e.eventType + " " + e.timestamp);
			engine.activity(e);
			e = engine.FEL.getFirstEvent();
		}
		
//		//syn modeling
//		Event p = Pay.checkFirstEvent();
//		Event ca = Cake.checkFirstEvent();
//		Event h = Hot.checkFirstEvent();
//		Event o = Cold.checkFirstEvent();
//		
//		double LBTS = 100000000;
//		
//		if (p.eventType == "pay")
//		LBTS = Math.min(LBTS, p.timestamp + OrderTime);
//		LBTS = Math.min(LBTS, ca.timestamp + HeatTime);
		
	}
}
