package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response of the records endpoint
 */
public class Record{
    public Record(){}

    @SerializedName("records")
    public Records records;
}
