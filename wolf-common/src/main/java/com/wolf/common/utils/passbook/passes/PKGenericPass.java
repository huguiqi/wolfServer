 
package com.wolf.common.utils.passbook.passes;

import com.wolf.common.utils.passbook.IPKValidateable;
import com.wolf.common.utils.passbook.PKField;

import java.util.ArrayList;
import java.util.List;


public class PKGenericPass implements IPKValidateable {

    protected List<PKField> headerFields;
    protected List<PKField> primaryFields;
    protected List<PKField> secondaryFields;
    protected List<PKField> auxiliaryFields;
    protected List<PKField> backFields;

    public List<PKField> getPrimaryFields() {
        return primaryFields;
    }

    public void setPrimaryFields(final List<PKField> primaryFields) {
        this.primaryFields = primaryFields;
    }

    public List<PKField> getSecondaryFields() {
        return secondaryFields;
    }

    public void setSecondaryFields(final List<PKField> secondaryFields) {
        this.secondaryFields = secondaryFields;
    }

    public List<PKField> getAuxiliaryFields() {
        return auxiliaryFields;
    }

    public void setAuxiliaryFields(final List<PKField> auxiliaryFields) {
        this.auxiliaryFields = auxiliaryFields;
    }

    public List<PKField> getBackFields() {
        return backFields;
    }

    public void setBackFields(final List<PKField> backFields) {
        this.backFields = backFields;
    }

    public List<PKField> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(final List<PKField> headerFields) {
        this.headerFields = headerFields;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public List<String> getValidationErrors() {

        List<String> validationErrors = new ArrayList<String>();

        List<List<PKField>> lists = new ArrayList<List<PKField>>();
        lists.add(primaryFields);
        lists.add(secondaryFields);
        lists.add(headerFields);
        lists.add(backFields);

        for (List<PKField> list : lists) {
            if (list != null) {
                for (PKField pkField : list) {
                    if (!pkField.isValid()) {
                        validationErrors.addAll(pkField.getValidationErrors());
                    }
                }
            }
        }

        return validationErrors;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
