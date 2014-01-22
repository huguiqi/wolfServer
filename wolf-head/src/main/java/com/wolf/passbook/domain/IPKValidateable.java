 
package com.wolf.passbook.domain;

import java.util.List;

public interface IPKValidateable {
    public boolean isValid();

    public List<String> getValidationErrors();
}
