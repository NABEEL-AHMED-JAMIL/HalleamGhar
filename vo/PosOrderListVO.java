package hg.vo;

import java.util.List;

import hg.model.PosOrder;

public class PosOrderListVO {
    private int pagesCount;
    private long totalPosOrders;

    private String actionMessage;
    private String searchMessage;

    private List<PosOrder> posOrders;

    public PosOrderListVO() {
    }

    public PosOrderListVO(int pages, long totalPosOrders, List<PosOrder> posOrders) {
        this.pagesCount = pages;
        this.setPosOrders(posOrders);
        this.totalPosOrders = totalPosOrders;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }


    public long getTotalContacts() {
        return totalPosOrders;
    }

    public void setTotalContacts(long totalContacts) {
        this.totalPosOrders = totalContacts;
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

	public List<PosOrder> getPosOrders() {
		return posOrders;
	}

	public void setPosOrders(List<PosOrder> posOrders) {
		this.posOrders = posOrders;
	}
}