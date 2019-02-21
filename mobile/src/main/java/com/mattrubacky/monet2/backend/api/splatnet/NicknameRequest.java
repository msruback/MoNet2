package com.mattrubacky.monet2.backend.api.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.data.deserialized.splatoon.NicknameIcons;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class NicknameRequest extends SplatnetRequest {

    private String id;
    private NicknameIcons icons;

    public NicknameRequest(String id){
        this.id = id;
        icons = new NicknameIcons();
        icons.nicknameIcons = new ArrayList<>();
    }

    @Override
    protected void manageResponse(Response response) {
        System.out.println(response.body());
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getNicknameIcon(id,cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("nickname",icons);
        return bundle;
    }
}
