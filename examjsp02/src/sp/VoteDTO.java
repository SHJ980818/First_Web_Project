package sp;



public class VoteDTO {
	
	private String item;
	private int count;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public VoteDTO(String item, int count) {
		super();
		this.item = item;
		this.count = count;
	}
	public VoteDTO() {}
	
}
