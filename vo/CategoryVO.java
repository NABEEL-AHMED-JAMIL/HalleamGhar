package hg.vo;
import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * A Item.
 */

public class CategoryVO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7353056229305507354L;

	private Long id;

    private String name;
    private String code;
    private String parentCategory;
    private Long parentCategoryId;
    private String description;
    private Boolean status;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryVO item = (CategoryVO) o;

        if (id!=null && ! id.equals(item.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category {" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                '}';
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parent) {
		this.parentCategory = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	
}
