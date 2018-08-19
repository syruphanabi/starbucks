public class FCFSQueue {
	Event head;
	Event tail;
	int lenght;
	
	public FCFSQueue() {
		this.head = null;
		this.tail = null;
		this.lenght = 0;
	}
	
	public void add(Event e) {
		if (this.head == null) {
			this.head = e;
			this.tail = e;
			this.lenght = 1;
		}else {
			this.tail.next = e;
			e.prev = this.tail;
			this.tail = e;
			this.lenght += 1;
		}
	}
	
	public Event get() {
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
	
	public Event check() {
		return this.head;
	}
	
	public boolean empty() {
		if (this.lenght == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getLenght() {
		return this.lenght;
	}
}

