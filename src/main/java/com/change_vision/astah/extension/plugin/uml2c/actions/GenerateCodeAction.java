package com.change_vision.astah.extension.plugin.uml2c.actions;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.velocity.exception.ResourceNotFoundException;

import com.change_vision.astah.extension.plugin.uml2c.Messages;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.AbstractCModule;
import com.change_vision.astah.extension.plugin.uml2c.cmodule.CModuleFactory;
import com.change_vision.astah.extension.plugin.uml2c.codegenerator.CodeGenerator;
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

public abstract class GenerateCodeAction implements IPluginActionDelegate {
    public Object run(IWindow window) throws UnExpectedException {
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
                JOptionPane.showMessageDialog(window.getParent(),
                        Messages.getMessage("message.select_class"), 
                        Messages.getMessage("title.select_class"),
                        JOptionPane.WARNING_MESSAGE);
                return null;
            }
            IClass iClass = (IClass) iElements[0];

            AbstractCModule cModule = CModuleFactory.getCModule(iClass);
            String outputDirPath = new File(projectAccessor.getProjectPath()).getParent();

            generateCode(cModule, outputDirPath);

        } catch (ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(window.getParent(), 
                    Messages.getMessage("message.project_not_found"), 
                    Messages.getMessage("title.project_not_found"),
                    JOptionPane.WARNING_MESSAGE);
        } catch (ResourceNotFoundException e) {
            JOptionPane.showMessageDialog(window.getParent(), 
                    Messages.getMessage("message.not_found_template", CodeGenerator.getAstahConfigPath(), e.getLocalizedMessage()),
                    Messages.getMessage("title.not_found_template"),
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(),
                    Messages.getMessage("message.unexpected_exception", e.getLocalizedMessage(), e.getStackTrace()),
                    Messages.getMessage("title.unexpected_exception"),
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    protected abstract void generateCode(AbstractCModule cModule, String outputDirPath) throws IOException;
}
