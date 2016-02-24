package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import com.change_vision.jude.api.inf.model.IClass;

public class CModuleFactory {

    public static AbstractCModule getCModule(IClass iClass) {
        if (iClass.hasStereotype("SingleInstance")) {
            return new SingleInstanceCModule(iClass);
        } else if (iClass.isAbstract()) {
            return new PerTypeDynamicInterfaceCModule(iClass);
        } else if (iClass.getGeneralizations().length == 1) {
            return new SubClassOfPerTypeDynamicInterfaceCModule(iClass);
        } else {
            return new MultiInstanceCModule(iClass);
        }
    }
}
