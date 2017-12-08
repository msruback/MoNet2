package com.mattrubacky.monet2.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.deserialized.NicknameIcons;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class NicknameRequest extends SplatnetRequest {

    private String id;
    private NicknameIcons icons;

    public NicknameRequest(String id){
        this.id = id;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        icons = (NicknameIcons) response.body();
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
