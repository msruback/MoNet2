package com.mattrubacky.monet2.connection;

import android.os.Bundle;

/**
 * Created by mattr on 1/9/2018.
 */

public interface WatchConnected {
    //The update method should be used to pull the data requested out of the bundle
    void update(Bundle bundle);
}
