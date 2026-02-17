package model;

public class Supply {
    private Integer id;
    private Integer astronautId;
    private SupplyType type;
    private Integer value;
     public Supply(){}

    public Supply(Integer id, Integer astronautId, SupplyType type, Integer value) {
        this.id = id;
        this.astronautId = astronautId;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAstronautId() {
        return astronautId;
    }

    public void setAstronautId(Integer astronautId) {
        this.astronautId = astronautId;
    }

    public SupplyType getType() {
        return type;
    }

    public void setType(SupplyType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
