package hg.vo;

import java.util.List;

import hg.model.Employee;
public class EmployeeListVO {
    private int pagesCount;
    private long totalEmployees;

    private String actionMessage;
    private String searchMessage;

    private List<Employee> employees;

    public EmployeeListVO() {
    }

    public EmployeeListVO(int pages, long totalEmployees, List<Employee> employees) {
        this.pagesCount = pages;
        this.employees = employees;
        this.totalEmployees = totalEmployees;
    }
    
    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

   

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
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
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}