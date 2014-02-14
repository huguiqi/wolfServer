 
package com.wolf.common.utils.passbook.passes;

import com.wolf.common.utils.passbook.enums.PKTransitType;

import java.util.List;


public class PKBoardingPass extends PKGenericPass {
    private PKTransitType transitType;

    public PKTransitType getTransitType() {
        return transitType;
    }

    public void setTransitType(final PKTransitType transitType) {
        this.transitType = transitType;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> validationErrors = super.getValidationErrors();
        if (transitType == null) {
            validationErrors.add("TransitType is not set");
        }
        return validationErrors;
    }
}
