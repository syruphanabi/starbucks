
public class Event {
	public double timestamp;
	public String eventType;
	public Customer cust;
	public Event next = null;
	public Event prev = null;
	
	Event(double time, Customer cust, String Type){
		this.timestamp = time;
		this.cust = cust;
		this.eventType = Type;
	}
}
