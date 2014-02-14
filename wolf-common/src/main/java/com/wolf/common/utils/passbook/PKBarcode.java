 

package com.wolf.common.utils.passbook;

import com.wolf.common.utils.passbook.enums.PKBarcodeFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class PKBarcode implements IPKValidateable {

    private PKBarcodeFormat format;
    private String altText;
    private String message;
    private Charset messageEncoding;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public PKBarcodeFormat getFormat() {
        return format;
    }

    public void setFormat(final PKBarcodeFormat format) {
        this.format = format;
    }

    public Charset getMessageEncoding() {
        return messageEncoding;
    }

    public void setMessageEncoding(final Charset messageEncoding) {
        this.messageEncoding = messageEncoding;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(final String altText) {
        this.altText = altText;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public List<String> getValidationErrors() {
        List<String> validationErrors = new ArrayList<String>();

        if (format == null || StringUtils.isEmpty(message) || messageEncoding == null) {
            validationErrors.add("Not all required Fields are set. Format: " + format + " Message: " + message + " MessageEncoding: "
                    + messageEncoding);
        }
        return validationErrors;
    }
}
