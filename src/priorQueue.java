
public class priorQueue {
	Event head;
	int lenght;
	
	public priorQueue() {
		this.head = null;
		this.lenght = 0;
	}
	
	public void addEvent(Event e) {
		if (this.head == null) {
			this.head = e;
			this.lenght = 1;
		}else {
			Event p = head;
			Event q = null;
			while (p!=null && (e.timestamp > p.timestamp)) {
				q = p;
				p = p.next;
			}
			if (p == null) {
				e.prev = q;
				q.next = e;
			}else {
				if (q == null) {
					e.next = p;
					p.prev = e;
					this.head = e;
				}else {
					e.next = p;
					q.next = e;
					e.prev = q;
					p.prev = e;
				}
			}
			this.lenght += 1;
			
		}
		
	}
	
	public Event getFirstEvent() {
		if (this.head == null) {
			return null;
		}
		Event result = this.head;
		this.head = result.next;
		if (this.head != null) {
			this.head.prev = null;
		}
		result.next = null;
		this.lenght -= 1;
		return result;
	}
	
	public Event checkFirstEvent() {
		return this.head;
	}
	
	public boolean empty() {
		if (this.lenght == 0) {
			return true;
		}else {
			return false;
		}
	}

}
