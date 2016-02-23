package com.change_vision.astah.extension.plugin.uml2c.cmodule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IOperation;
import com.change_vision.jude.api.inf.model.IParameter;

public abstract class AbstractCModule {

    public AbstractCModule(IClass iClass) {
        this.enumerations = new ArrayList<CEnumeration>();
        for (IClass c : iClass.getNestedClasses()) {
            String[] stereotypes = c.getStereotypes();
            if ((stereotypes.length == 1) && stereotypes[0].equals("enumeration")) {
                enumerations.add(new CEnumeration(c));
            }
        }

        this.iClass = iClass;
    }

    public String getName() {
        return iClass.getName();
    }

    public List<String> getPrototypeDeclarations() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = o.getReturnType() + " " + o.getName() + "(";
                if (!o.isStatic()) {
                    s += iClass.getName() + " " + selfSymbol;
                    if (o.getParameters().length > 0) {
                        s += ", " + getParameterDeclaration(o.getParameters());
                    }
                } else {
                    if (o.getParameters().length > 0) {
                        s += getParameterDeclaration(o.getParameters());
                    }
                }
                s += ")";
                list.add(s);
            }
        }
        return list;
    }

    public List<String> getPrototypeDeclarationsForC() {
        List<String> list = new ArrayList<String>();
        for (String s : getPrototypeDeclarations()) {
            s = s.replaceFirst(" ", " " + iClass.getName() + "_");
            s = s.replaceFirst("\\(\\)", "\\(void\\)");
            list.add(s);
        }
        return list;
    }

    public List<String> getPrototypeDeclarationsForCInterface() {
        return null;
    }

    public List<String> getMethodDeclarationsForMock() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = "MOCK_METHOD";
                if (!o.isStatic()) {
                    s += (o.getParameters().length + 1);
                } else {
                    s += o.getParameters().length;
                }
                s += "(";
                s += o.getName() + ", " + o.getReturnType() + "(";
                if (!o.isStatic()) {
                    s += iClass.getName() + " " + selfSymbol;
                    if (o.getParameters().length > 0) {
                        s += ", " + getParameterDeclaration(o.getParameters());
                    }
                } else {
                    if (o.getParameters().length > 0) {
                        s += getParameterDeclaration(o.getParameters());
                    }
                }
                s += "))";
                list.add(s);
            }
        }
        return list;
    }

    public List<String> getStaticMemberDefinitions() {
        List<String> list = new ArrayList<String>();
        for (IAttribute a : iClass.getAttributes()) {
            if (a.getNavigability() != "Navigable")
                continue;
            if (!a.isStatic())
                continue;
            list.add(getMemberDefinitionCommon(a));
        }
        return list;
    }

    public List<String> getMemberDefinitions() {
        List<String> list = new ArrayList<String>();
        for (IAttribute a : iClass.getAttributes()) {
            if (Arrays.asList(a.getStereotypes()).contains("macro"))
                continue;
            if (a.getNavigability() != "Navigable")
                continue;
            if (a.isStatic())
                continue;
            list.add(getMemberDefinitionCommon(a));
        }
        return list;
    }

    public List<String> getCallMockFunctionDefinitions() {
        List<String> list = new ArrayList<String>();
        for (IOperation o : iClass.getOperations()) {
            if (o.isPublicVisibility()) {
                String s = o.getReturnType() + " " + iClass.getName() + "_" + o.getName() + "(";
                if (!o.isStatic()) {
                    s += iClass.getName() + " " + selfSymbol;
                    if (o.getParameters().length > 0) {
                        s += ", " + getParameterDeclaration(o.getParameters());
                    }
                } else {
                    s += (o.getParameters().length > 0) ? getParameterDeclaration(o.getParameters())
                            : "void";
                }
                s += String.format(")%n{%n\t");
                if (!o.getReturnType().toString().equals("void"))
                    s += "return ";
                s += "g_p" + iClass.getName() + "->" + o.getName() + "(";
                if (!o.isStatic())
                    s += selfSymbol;
                for (IParameter p : o.getParameters())
                    s += ", " + p.getName();
                s += String.format(");%n}");
                list.add(s);
            }
        }
        return list;
    }

    public List<CEnumeration> getEnumerations() {
        return enumerations;
    }

    protected String getParameterDeclaration(IParameter[] parameters) {
        String s = "";
        for (IParameter p : parameters) {
            if ((p.getDirection().equals("in")) && (p.getTypeModifier().equals("*"))) {
                s += "const ";
            }
            s += p.getType().getName() + " " + p.getTypeModifier() + p.getName() + ", ";
        }
        StringBuilder sb = new StringBuilder(s);
        if (parameters.length > 0) {
            sb = sb.delete(sb.lastIndexOf(", "), sb.length());
        }
        return sb.toString();
    }

    protected String getMemberDefinitionCommon(IAttribute a) {
        String s = a.getType().getName();
        IAssociation association = a.getAssociation();
        if (association != null) {
            boolean isMultiInstance = (association.getMemberEnds()[1].getType().getStereotypes().length == 0);
            boolean isPointer = !association.getMemberEnds()[0].isComposite();
            if (isMultiInstance) {
                if (isPointer) {
                    s += " *";
                } else {
                    s += "Struct ";
                }
            } else {
                s += isPointer ? " *" : " ";
            }
        } else {
            s += " " + a.getTypeModifier();
        }

        s += a.getName();
        if ((a.getMultiplicity().length > 0) && (a.getMultiplicity()[0].getUpper() != 1)) {
            s += "[ /* TODO */ ]";
        }

        return s;
    }

    protected IClass iClass;
    protected String selfSymbol = "self";
    private List<CEnumeration> enumerations;
}
