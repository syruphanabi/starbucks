


public class Customer {
	//int NumOfProduct;
	
	public int index;
	String OrderType;
	public double arrTime;
	public double leaveTime = -1;
	public int numCustWait;
	
	public Customer(int index, double arrTime, String Type){
		this.index = index;
		this.arrTime = arrTime;
		this.OrderType = Type;
	}
	
	public void lookAtLine(int num) {
		numCustWait = num;
	}
	
}
