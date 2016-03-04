package com.change_vision.astah.extension.plugin.uml2c.astahutil;

import com.change_vision.jude.api.inf.AstahAPI;

public class AstahUtil {
    public static String getFullPathOfConfigDir() {
        String edition;
        try {
            edition = AstahAPI.getAstahAPI().getProjectAccessor().getAstahEdition();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        String userHomeDir = System.getProperty("user.home");
        return userHomeDir + "/.astah/" + edition;
    }
}
