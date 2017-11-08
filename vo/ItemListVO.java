package hg.vo;

import java.util.List;
public class ItemListVO {
    private int pagesCount;
    private long totalItems;

    private String actionMessage;
    private String searchMessage;

    private List<ItemVO> items;

    public ItemListVO() {
    }

    public ItemListVO(int pages, long totalItems, List<ItemVO> items) {
        this.pagesCount = pages;
        this.items = items;
        this.totalItems = totalItems;
    }
    
    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

   

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
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

	public List<ItemVO> getItems() {
		return items;
	}

	public void setItems(List<ItemVO> items) {
		this.items = items;
	}
}