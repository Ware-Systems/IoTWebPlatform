package com.deviceiot.platform.iot.message;

import com.amazonaws.services.iot.client.*;

import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/9/17.
 */
@Slf4j
public class MyLampShadowMessage extends AWSIotMessage {

    public MyLampShadowMessage() {
        super(null, null);
    }

    @Override
    public void onSuccess() {
        // called when the shadow method succeeded
        // state (JSON document) received is available in the payload field
        log.debug("onSuccess Message -- {} ", getStringPayload());
    }

    @Override
    public void onFailure() {
        // called when the shadow method failed
        log.debug("onFailure Message -- {} ", getStringPayload());
    }

    @Override
    public void onTimeout() {
        // called when the shadow method timed out
        log.debug("onTimeout Message -- {} ", getStringPayload());
    }

}
