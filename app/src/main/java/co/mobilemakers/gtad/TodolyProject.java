package co.mobilemakers.gtad;

import com.google.gson.annotations.SerializedName;

/**
 * Created by diana.perez on 11/02/2015.
 */
public class TodolyProject {

    @SerializedName("Id")
    private String mId;
    @SerializedName("Content")
    private String mName;

    public TodolyProject(){

    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public String toString() {
        return "(ID="+ mId + "): " + mName;
    }
}
