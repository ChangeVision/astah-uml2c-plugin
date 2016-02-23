package com.change_vision.astah.extension.plugin.uml2c.actions;

import java.io.IOException;

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.astah.extension.plugin.uml2c.codegenerator.GoogleMockGenerator;

public class GenerateGoogleMockAction extends GenerateCodeAction {
    @Override
    protected void generateCode(AbstractCModule cModule, String outputDirPath) throws IOException {
        GoogleMockGenerator googleMockGenerator = new GoogleMockGenerator(cModule);
        googleMockGenerator.outputHeader(outputDirPath);
        googleMockGenerator.outputSource(outputDirPath);
    }
}
