package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IGeneralization;

public class CModuleFactory {

    public static AbstractCModule getCModule(IClass iClass) {
        assert (iClass.getStereotypes().length <= 1);
        assert (iClass.getGeneralizations().length <= 1);

        AbstractCModule module = null;
        String[] stereotypes = iClass.getStereotypes();
        IGeneralization[] generalizations = iClass.getGeneralizations();

        if ((stereotypes.length == 1) && (stereotypes[0].equals("SingleInstance"))) {
            module = new SingleInstanceCModule(iClass);
        } else if (iClass.isAbstract()) {
            module = new PerTypeDynamicInterfaceCModule(iClass);
        } else if (generalizations.length == 1) {
            module = new SubClassOfPerTypeDynamicInterfaceCModule(iClass);
        } else {
            module = new MultiInstanceCModule(iClass);
        }
        System.out.printf("Module is %s.\n", module.getClass().getSimpleName());
        return module;
    }
}
