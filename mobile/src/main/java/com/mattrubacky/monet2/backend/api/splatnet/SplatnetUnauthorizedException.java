package com.mattrubacky.monet2.backend.api.splatnet;

/**
 * Created by mattr on 12/6/2017.
 */

class SplatnetUnauthorizedException extends Exception{
    SplatnetUnauthorizedException(String message){
        super(message);
    }
}
