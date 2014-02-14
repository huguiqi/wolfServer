 

package com.wolf.common.utils.passbook;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class PKLocation implements IPKValidateable {
    private double latitude;
    private double longitude;
    private double altitude;
    private String relevantText;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(final double altitude) {
        this.altitude = altitude;
    }

    public String getRelevantText() {
        return relevantText;
    }

    public void setRelevantText(final String relevantText) {
        this.relevantText = relevantText;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public List<String> getValidationErrors() {

        List<String> validationErrors = new ArrayList<String>();
        if (longitude == 0 || latitude == 0) {
            validationErrors.add("Not all required Fields are set: longitude, latitude");
        }
        return validationErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
