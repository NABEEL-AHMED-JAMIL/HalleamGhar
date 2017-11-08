package hg.model;

public class SimpleVO {
	private Double amount;
	private String date;
	
	public SimpleVO(Double amount, String date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
