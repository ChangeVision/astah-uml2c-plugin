package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IClass;

class PerTypeDynamicInterfaceCModule extends AbstractCModule {
    PerTypeDynamicInterfaceCModule(IClass iClass) {
        super(iClass);
    }

    @Override
    public List<String> getPrototypeDeclarationsForCInterface() {
        List<String> list = new ArrayList<String>();
        for (String s : getPrototypeDeclarations()) {
            s = s.replaceFirst("\\(", "\\)\\(");
            s = s.replaceFirst(" ", " \\(*");
            list.add(s);
        }
        return list;
    }
}