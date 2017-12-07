package com.mattrubacky.monet2.splatnet;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by mattr on 12/6/2017.
 */

public interface SplatnetRequest {
    public void run() throws SplatnetUnauthorizedException,MalformedURLException,IOException;
}
