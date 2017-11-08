package hg.vo;

import java.util.List;
public class CategoryListVO {
    private int pagesCount;
    private long totalCategories;

    private String actionMessage;
    private String searchMessage;

    private List<CategoryVO> categories;

    public CategoryListVO() {
    }

    public CategoryListVO(int pages, long totalCategories, List<CategoryVO> categories) {
        this.pagesCount = pages;
        this.setCategories(categories);
        this.totalCategories = totalCategories;
    }
    
    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

   

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalItems) {
        this.totalCategories = totalItems;
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

	public List<CategoryVO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryVO> categories) {
		this.categories = categories;
	}

}