package hg.vo;

import java.util.List;
public class CustomerListVO {
    private int pagesCount;
    private long totalCustomers;

    private String actionMessage;
    private String searchMessage;

    private List<CustomerVO> items;

    public CustomerListVO() {
    }

    public CustomerListVO(int pages, long totalCustomers, List<CustomerVO> items) {
        this.pagesCount = pages;
        this.items = items;
        this.totalCustomers = totalCustomers;
    }
    
    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

   

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

	public List<CustomerVO> getCustomers() {
		return items;
	}

	public void setCustomers(List<CustomerVO> items) {
		this.items = items;
	}
}