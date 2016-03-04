package com.change_vision.astah.extension.plugin.uml2c.codegenerator;

import java.io.IOException;

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;

public class GoogleMockGenerator {

    public GoogleMockGenerator(AbstractCModule cModule) {
        this.codeGenerator = new CodeGenerator(cModule);
    }

    public void outputHeader(String dirPath) throws IOException, InterruptedException {
        String codePath = dirPath + "/Mock_" + codeGenerator.getCModuleName() + ".h";
        String templatePath = codeGenerator.getTemplateDirPath() + "mock-header.vm";
        codeGenerator.outputCode(codePath, templatePath);
    }

    public void outputSource(String dirPath) throws IOException, InterruptedException {
        String codePath = dirPath + "/Mock_" + codeGenerator.getCModuleName() + ".cpp";
        String templatePath = codeGenerator.getTemplateDirPath() + "mock-source.vm";
        codeGenerator.outputCode(codePath, templatePath);
    }

    private CodeGenerator codeGenerator;
}
