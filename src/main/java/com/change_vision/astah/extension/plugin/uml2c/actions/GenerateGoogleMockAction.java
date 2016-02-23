package com.change_vision.astah.extension.plugin.uml2c.actions;

import java.io.File;

import javax.swing.JOptionPane;

import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.CModuleFactory;
import com.change_vision.astah.extension.plugin.uml2c.codegenerator.GoogleMockGenerator;
import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public class GenerateGoogleMockAction implements IPluginActionDelegate {
    public Object run(IWindow window) throws UnExpectedException {
        System.out.println("Start generating gmock.");

        try {
            AstahAPI api = AstahAPI.getAstahAPI();
            ProjectAccessor projectAccessor = api.getProjectAccessor();
            @SuppressWarnings("unused")
            IModel iCurrentProject = projectAccessor.getProject();

            // 選択されたモデル要素を取得
            IViewManager iViewManager = api.getViewManager();
            IDiagramViewManager iDiagramViewManager = iViewManager.getDiagramViewManager();
            IElement iElements[] = iDiagramViewManager.getSelectedElements();

            // 選択されたモデル要素が1つ、クラスであることをチェック
            if ((iElements.length != 1) || !(iElements[0] instanceof IClass)) {
                JOptionPane.showMessageDialog(window.getParent(), "Please select only 1 class.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            IClass iClass = (IClass) iElements[0];

            AbstractCModule cModule = CModuleFactory.getCModule(iClass);
            String outputDirPath = new File(projectAccessor.getProjectPath()).getParent();

            GoogleMockGenerator googleMockGenerator = new GoogleMockGenerator(cModule);
            googleMockGenerator.outputHeader(outputDirPath);
            googleMockGenerator.outputSource(outputDirPath);

        } catch (ProjectNotFoundException e) {
            String message = "Please open a project";
            JOptionPane.showMessageDialog(window.getParent(), message, "Warning",
                    JOptionPane.WARNING_MESSAGE);

            throw new CalculateUnExpectedException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), "Exception Occured\n\n" + e.getLocalizedMessage() + "\n" + e.getStackTrace(), "Alert",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            throw new UnExpectedException();
        }

        System.out.println("End generating gmock.");
        return null;
    }

    @SuppressWarnings("serial")
    public class CalculateUnExpectedException extends UnExpectedException {
    }
}
