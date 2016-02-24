package com.change_vision.astah.extension.plugin.uml2c;

import com.change_vision.jude.api.inf.AstahAPI;

import ch.qos.logback.core.PropertyDefinerBase;

public class AstahEditionPropertyDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        String edition = getEditionName();
        if (edition.isEmpty()) {
            edition = "professional";
        }
        return edition;
    }
    
    private String getEditionName() {
        try {
            return AstahAPI.getAstahAPI().getProjectAccessor().getAstahEdition();
        } catch (ClassNotFoundException e) {
            return "";
        }
    }
}