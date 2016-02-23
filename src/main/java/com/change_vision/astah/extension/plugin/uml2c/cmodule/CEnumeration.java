package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;

public class CEnumeration {

    public CEnumeration(IClass iClass) {
        String[] stereotypes = iClass.getStereotypes();
        assert ((stereotypes.length == 1) && stereotypes[0].equals("enumeration"));
        this.iClass = iClass;
    }

    public String getName() {
        return iClass.getName();
    }

    public List<IAttribute> getEnumerators() {
        List<IAttribute> list = new ArrayList<IAttribute>();
        for (IAttribute a : iClass.getAttributes()) {
            if (a.getNavigability() != "Navigable")
                continue;
            list.add(a);
        }
        return list;
    }

    private IClass iClass;
}
