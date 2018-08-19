
public class Engine 
{
//	double OrderTime = 80;
//	double CakeDeliTime = 25;
//	double ColdFillTime = 45;
//	double HotFillTime = 70;
//	double PickUpTime = 30;
//	double HeatTime = 150;
	double OrderTime;
	double CakeDeliTime;
	double ColdFillTime;
	double HotFillTime;
	double PickUpTime;
	double HeatTime;
	stat cse6730;
	
	public Engine(double[] engineInfo, stat stat) 
	{
		this.FEL = new priorQueue();
		OrderTime = engineInfo[0];
		CakeDeliTime = engineInfo[1];
		ColdFillTime = engineInfo[2];
		HotFillTime = engineInfo[3];
		PickUpTime = engineInfo[4];
		HeatTime = engineInfo[5];
		cse6730 = stat;
	}
	
	priorQueue FEL;
	
	Resource casher = new Resource();
	Resource coldLine = new Resource();
	Resource hotLine = new Resource();
	
	FCFSQueue Pay = new FCFSQueue();
	FCFSQueue Cake = new FCFSQueue();
	FCFSQueue Hot = new FCFSQueue();
	FCFSQueue Cold = new FCFSQueue();

	public void activity(Event e) {
		if (e.eventType == "arrival") Arrival(e);
		
		else if (e.eventType == "pay") Pay(e);

		else if (e.eventType == "heated") Heated(e);
		
		else if (e.eventType == "casherDone") CasherDone(e);
		
		else if (e.eventType == "coldFill") ColdFill(e);
		
		else if (e.eventType == "hotFill") HotFill(e);
		
		else if (e.eventType == "coldCall") ColdCall(e);
		
		else if (e.eventType == "hotCall") HotCall(e);
		
		else if (e.eventType == "giveCake") GiveCake(e);
		
		else if (e.eventType == "leave") Leave(e);
		
		else System.out.print("wrong event type");
	}
	
	public void Arrival(Event e) 
	{
		e.cust.numCustWait = Pay.getLenght();
		if (casher.Free && Pay.empty()) FEL.addEvent(new Event(e.timestamp, e.cust, "pay"));
		else Pay.add(new Event(-1, e.cust, "inPay"));
	}
	
	public void Pay(Event e) 
	{
		casher.Free = false;
		FEL.addEvent(new Event(e.timestamp + OrderTime, e.cust, "casherDone"));
		if (e.cust.OrderType == "cake") 
		{
			FEL.addEvent(new Event(e.timestamp + OrderTime + HeatTime, e.cust, "heated"));
		}
		else if (e.cust.OrderType == "sandwich") 
		{
			FEL.addEvent(new Event(e.timestamp + OrderTime, e.cust, "leave"));
		}
		else if (e.cust.OrderType == "cold") 
		{
			if (coldLine.Free && Cold.empty()) 
			{
				FEL.addEvent(new Event(e.timestamp + OrderTime, e.cust, "coldFill"));
			}
			else 
			{
				Cold.add(new Event(-1, e.cust, "inCold"));
			}
		}
		else if (e.cust.OrderType == "hot") 
		{
			if (hotLine.Free && Hot.empty()) 
			{
				FEL.addEvent(new Event(e.timestamp + OrderTime, e.cust, "hotFill"));
			}
			else 
			{
				Hot.add(new Event(-1, e.cust, "inHot"));
			}
			
		}
		else
		{
			System.out.print("wrong Ordertype");
		}
	}
	
	private void Heated(Event e) {
		if (!casher.Free) Cake.add(new Event(-1, e.cust, "inCake"));
		else 
		{
			Cake.add(new Event(-1, e.cust, "inCake"));
			FEL.addEvent(new Event(e.timestamp, e.cust, "giveCake"));
		}
	}

	public void CasherDone(Event e) {
		casher.Free = true;
		if (!Cake.empty()) FEL.addEvent(new Event(e.timestamp, Cake.check().cust, "giveCake"));
		else if (!Pay.empty())
		{
			Event temp = Pay.get();
			FEL.addEvent(new Event(e.timestamp, temp.cust, "pay"));
		}
	}
	
	private void GiveCake(Event e) 
	{
		casher.Free = false;
		FEL.addEvent(new Event(e.timestamp + CakeDeliTime, e.cust, "casherDone"));
		while (!Cake.empty()) 
		{
			Event give = Cake.get();
			FEL.addEvent(new Event(e.timestamp + CakeDeliTime, give.cust, "leave"));
		}
	}

	private void HotFill(Event e) {
		hotLine.Free = false;
		FEL.addEvent(new Event(e.timestamp + HotFillTime, e.cust, "hotCall"));
	}

	private void ColdFill(Event e) {
		coldLine.Free = false;
		FEL.addEvent(new Event(e.timestamp + ColdFillTime, e.cust, "coldCall"));
	}
	
	private void HotCall(Event e) {
		hotLine.Free = true;
		FEL.addEvent(new Event(e.timestamp + PickUpTime, e.cust, "leave"));
		if (!Hot.empty()) 
		{
			Event temp = Hot.get();
			FEL.addEvent(new Event(e.timestamp, temp.cust, "hotFill"));
		}
	}

	private void ColdCall(Event e) {
		coldLine.Free = true;
		FEL.addEvent(new Event(e.timestamp + PickUpTime, e.cust, "leave"));
		if (!Cold.empty()) 
		{
			Event temp = Cold.get();
			FEL.addEvent(new Event(e.timestamp, temp.cust, "coldFill"));
		}
		
	}

	private void Leave(Event e) {
		e.cust.leaveTime = e.timestamp;
		System.out.println("Customer No." + e.cust.index + " waits for " + e.cust.numCustWait 
				+ " others, and gets " + e.cust.OrderType + " in "
				+ (e.cust.leaveTime - e.cust.arrTime) + " seconds.");
		cse6730.record(e.cust.numCustWait, (e.cust.leaveTime - e.cust.arrTime));
	}

}
