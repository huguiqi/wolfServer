 

package com.wolf.common.utils.passbook;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PKPushToken {
    private String pushToken;

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(final String pushToken) {
        this.pushToken = pushToken;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
