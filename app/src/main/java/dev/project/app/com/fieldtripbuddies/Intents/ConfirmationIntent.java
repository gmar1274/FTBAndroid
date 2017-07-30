package dev.project.app.com.fieldtripbuddies.Intents;

import android.os.Parcel;
import android.os.Parcelable;

import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;
import dev.project.app.com.fieldtripbuddies.Objects.API_Candidate;

/**
 * Created by gabe on 7/30/2017.
 */

public class ConfirmationIntent implements Parcelable{
    private boolean flag;
    private API_Candidate candidate;
    public static final String CONFIRMATION_INTENT = "confirmation_intent";

    public ConfirmationIntent(IAPI_Candidate cand, boolean flag){
        this.flag = flag;
        this.candidate = (API_Candidate) cand;
    }

    protected ConfirmationIntent(Parcel in) {
        flag = in.readByte() != 0;
        candidate = in.readParcelable(API_Candidate.class.getClassLoader());
    }

    public static final Creator<ConfirmationIntent> CREATOR = new Creator<ConfirmationIntent>() {
        @Override
        public ConfirmationIntent createFromParcel(Parcel in) {
            return new ConfirmationIntent(in);
        }

        @Override
        public ConfirmationIntent[] newArray(int size) {
            return new ConfirmationIntent[size];
        }
    };

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public API_Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(API_Candidate candidate) {
        this.candidate = candidate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (flag ? 1 : 0));
        parcel.writeParcelable(candidate, i);
    }
}
