package com.change_vision.astah.extension.plugin.uml2c.codegenerator;

import java.io.IOException;

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;

public class CSkeletonGenerator {

    public CSkeletonGenerator(AbstractCModule cModule) {
        this.codeGenerator = new CodeGenerator(cModule);
    }

    public void outputHeader(String dirPath) throws IOException, InterruptedException {
        String codePath = dirPath + "/" + codeGenerator.getCModuleName() + ".h";
        String templatePath = codeGenerator.getTemplateDirPath() + "header.vm";
        codeGenerator.outputCode(codePath, templatePath);
    }

    public void outputPrivateHeader(String dirPath) throws IOException, InterruptedException {
        String codePath = dirPath + "/" + codeGenerator.getCModuleName() + "Private.h";
        String templatePath = codeGenerator.getTemplateDirPath() + "private-header.vm";
        codeGenerator.outputCode(codePath, templatePath);
    }

    public void outputSource(String dirPath) throws IOException, InterruptedException {
        String codePath = dirPath + "/" + codeGenerator.getCModuleName() + ".c";
        String templatePath = codeGenerator.getTemplateDirPath() + "source.vm";
        codeGenerator.outputCode(codePath, templatePath);
    }

    private CodeGenerator codeGenerator;
}
