package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import com.change_vision.jude.api.inf.model.IClass;

class SubClassOfPerTypeDynamicInterfaceCModule extends AbstractCModule {
    SubClassOfPerTypeDynamicInterfaceCModule(IClass iClass) {
        super(iClass);
        this.selfSymbol = "super";
    }
}
