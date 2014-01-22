 
package com.wolf.passbook.domain.passes;

import java.util.List;

import com.wolf.passbook.domain.enums.PKTransitType;


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
