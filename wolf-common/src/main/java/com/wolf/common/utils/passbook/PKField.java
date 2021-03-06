 
package com.wolf.common.utils.passbook;

import com.wolf.common.utils.passbook.enums.PKDateStyle;
import com.wolf.common.utils.passbook.enums.PKNumberStyle;
import com.wolf.common.utils.passbook.enums.PKTextAlignment;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PKField implements IPKValidateable {
    private String key;
    private String label;
    private Object value;
    private String changeMessage;
    private PKTextAlignment textAlignment;

    private String currencyCode;
    private PKNumberStyle numberStyle;

    private PKDateStyle dateStyle;
    private PKDateStyle timeStyle;
    private boolean isRelative;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public String getChangeMessage() {
        return changeMessage;
    }

    public void setChangeMessage(final String changeMessage) {
        this.changeMessage = changeMessage;
    }

    public PKTextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(final PKTextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public PKNumberStyle getNumberStyle() {
        return numberStyle;
    }

    public void setNumberStyle(final PKNumberStyle numberStyle) {
        this.numberStyle = numberStyle;
    }

    public PKDateStyle getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(final PKDateStyle dateStyle) {
        this.dateStyle = dateStyle;
    }

    public PKDateStyle getTimeStyle() {
        return timeStyle;
    }

    public void setTimeStyle(final PKDateStyle timeStyle) {
        this.timeStyle = timeStyle;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(final boolean isRelative) {
        this.isRelative = isRelative;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public List<String> getValidationErrors() {

        List<String> validationErrors = new ArrayList<String>();
        if (value == null || StringUtils.isEmpty(key)) {
            validationErrors.add("Not all required Fields are set. Key: " + key + " Value:" + value);
        }
        if (!(value instanceof String || value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double
                || value instanceof Date || value instanceof BigDecimal)) {
            validationErrors.add("Invalid value type: String, Integer, Float, Long, Double, java.util.Date, BigDecimal");
        }
        if (currencyCode != null && numberStyle != null) {
            validationErrors.add("CurrencyCode and numberStyle are both set");
        }
        if ((currencyCode != null || numberStyle != null) && (dateStyle != null || timeStyle != null)) {
            validationErrors.add("Can't be number/currency and date at the same time");
        }
        if (changeMessage != null && !changeMessage.contains("%@")) {
            validationErrors.add("ChangeMessage needs to contain %@ placeholder");
        }
        return validationErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
