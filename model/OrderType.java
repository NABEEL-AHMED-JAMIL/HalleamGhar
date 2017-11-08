package hg.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum OrderType {
	TAKEAWAY("TAKEAWAY"),
	DINEIN("DINEIN"),
	DELIVERY("Delivery"),
	CATERING("Catering");
	private String value;  
    private OrderType(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return this.value; }

    @JsonCreator
    public static OrderType create(String val) {
    	OrderType[] units = OrderType.values();
        for (OrderType unit : units) {
            if (unit.getValue().equals(val)) {
                return unit;
            }
        }
        return TAKEAWAY;
    }
}
