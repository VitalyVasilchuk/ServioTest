package apps.basilisk.serviotest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Places {

    @SerializedName("PlaceUnions")
    @Expose
    private List<PlaceUnion> placeUnions = null;

    public List<PlaceUnion> getPlaceUnions() {
        return placeUnions;
    }

    public void setPlaceUnions(List<PlaceUnion> placeUnions) {
        this.placeUnions = placeUnions;
    }
}
