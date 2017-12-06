package com.patrickfeltes.graphingprogram.graphs;

import android.os.Parcel;
import android.os.Parcelable;

public class Equation implements Parcelable {

    private String equation;

    public Equation(String equation) {
        this.equation = equation;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.equation);
    }

    protected Equation(Parcel in) {
        this.equation = in.readString();
    }

    public static final Creator<Equation> CREATOR = new Creator<Equation>() {
        @Override
        public Equation createFromParcel(Parcel source) {
            return new Equation(source);
        }

        @Override
        public Equation[] newArray(int size) {
            return new Equation[size];
        }
    };
}
