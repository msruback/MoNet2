package com.mattrubacky.monet2.data.deserialized.splatoon;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class is the root of the results endpoint
 */

public class ResultList {
    public ResultList(){}

    @SerializedName("results")
    public ArrayList<ResultIds> resultIds;
}
