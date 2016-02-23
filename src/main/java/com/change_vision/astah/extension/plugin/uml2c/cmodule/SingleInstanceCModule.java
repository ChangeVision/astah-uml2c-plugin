package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IOperation;
import com.change_vision.jude.api.inf.model.IParameter;

class SingleInstanceCModule extends AbstractCModule {

    SingleInstanceCModule(IClass iClass) {
        super(iClass);
    }

    @Override
    public List<String> getPrototypeDeclarations() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = o.getReturnType() + " " + o.getName() + "(";
                if (o.getParameters().length > 0) {
                    s += getParameterDeclaration(o.getParameters());
                }
                s += ")";
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public List<String> getMethodDeclarationsForMock() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = "MOCK_METHOD";
                s += o.getParameters().length;
                s += "(";
                s += o.getName() + ", " + o.getReturnType() + "(";
                if (o.getParameters().length > 0) {
                    s += getParameterDeclaration(o.getParameters());
                }
                s += "))";
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public List<String> getMemberDefinitions() {
        List<String> list = new ArrayList<String>();
        return list; /* return empty list */
    }

    @Override
    public List<String> getCallMockFunctionDefinitions() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = o.getReturnType() + " " + iClass.getName() + "_" + o.getName() + "(";

                s += (o.getParameters().length > 0) ? getParameterDeclaration(o.getParameters())
                        : "void";
                s += String.format(")%n{%n\t");
                if (!o.getReturnType().toString().equals("void"))
                    s += "return ";
                s += "g_p" + iClass.getName() + "->" + o.getName() + "(";

                for (IParameter p : o.getParameters())
                    s += p.getName() + ", ";
                s += String.format(");%n}");
                s = s.replaceFirst(", \\)", " \\)");

                list.add(s);
            }
        }
        return list;
    }

    @Override
    public List<String> getStaticMemberDefinitions() {
        List<String> list = new ArrayList<String>();
        for (IAttribute a : iClass.getAttributes()) {
            if (a.getNavigability() != "Navigable")
                continue;
            String s = getMemberDefinitionCommon(a);
            if (!a.getInitialValue().isEmpty()) {
                s += " = " + a.getInitialValue();
            }
            list.add(s);
        }
        return list;
    }
}