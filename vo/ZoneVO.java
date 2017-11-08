package hg.vo;
import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * A Item.
 */

public class ZoneVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7244744190836299317L;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	private int id;

    private String name;

    private String cellNo;

    private String address;

    private String remarks;

    private String profile;


    public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZoneVO item = (ZoneVO) o;

        if ( ! (id == item.id)) return false;

        return true;
    }

	
	
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CusotmerVO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", address='" + address + "'" +
                '}';
    }

	public String getProfile() {
		return 	"<div>" +cellNo + "</div>" +
				"<div>" +address + "</div>" ;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
