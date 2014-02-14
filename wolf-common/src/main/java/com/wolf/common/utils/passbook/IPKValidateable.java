 
package com.jje.gateway.util.passbook;

import java.util.List;

public interface IPKValidateable {
    public boolean isValid();

    public List<String> getValidationErrors();
}
