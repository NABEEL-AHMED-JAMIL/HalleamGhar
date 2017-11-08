package hg.vo;

import java.util.List;

import hg.model.Zone;
public class ZoneListVO {
    private int pagesCount;
    private long totalZones;

    private String actionMessage;
    private String searchMessage;

    private List<Zone> items;

    public ZoneListVO() {
    }

    public ZoneListVO(int pages, long totalZones, List<Zone> items) {
        this.pagesCount = pages;
        this.items = items;
        this.totalZones = totalZones;
    }
    
    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

   

    public long getTotalZones() {
        return totalZones;
    }

    public void setTotalZones(long totalZones) {
        this.totalZones = totalZones;
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

	public List<Zone> getZones() {
		return items;
	}

	public void setZones(List<Zone> items) {
		this.items = items;
	}
}