package com.mattrubacky.monet2.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeserializedHelper{
    public String[] getCSV(String path) throws IOException {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(path);
        InputStreamReader in = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(in);
        StringBuilder stringBuilder = new StringBuilder();

        String temp = "";

        while((temp = br.readLine())!=null){
            stringBuilder.append(temp);
        }
        return stringBuilder.toString().split(",");
    }

    public String getJSON(String path) throws IOException {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(path);
        InputStreamReader in = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(in);
        StringBuilder stringBuilder = new StringBuilder();

        String temp = "";

        while((temp = br.readLine())!=null){
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}
