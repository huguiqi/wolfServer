 
package com.wolf.common.utils.passbook;

import java.util.List;

public interface IPKValidateable {
    public boolean isValid();

    public List<String> getValidationErrors();
}
