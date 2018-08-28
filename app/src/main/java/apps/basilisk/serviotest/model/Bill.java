
package apps.basilisk.serviotest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bill implements Serializable {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Opened")
    @Expose
    private String opened;
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("OpenUser")
    @Expose
    private String openUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getOpenUser() {
        return openUser;
    }

    public void setOpenUser(String openUser) {
        this.openUser = openUser;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", number=" + number +
                ", opened='" + opened + '\'' +
                ", total=" + total +
                ", openUser='" + openUser + '\'' +
                '}';
    }
}
