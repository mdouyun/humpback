/*
 * Copyright (c) 2014 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thorn.humpback.codebuilder.action;

import org.apache.commons.lang3.StringUtils;
import org.thorn.humpback.codebuilder.entity.*;
import org.thorn.humpback.codebuilder.service.TemplateHandler;
import org.thorn.humpback.codebuilder.view.RenderTemplatePanel;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成界面完成按钮对应的操作, 该操作根据模板文件及前一步的字段数据执行最终的代码生成.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class RenderTemplateAction extends AbsAction {

    private RenderTemplatePanel templatePanel;

    public RenderTemplateAction(RenderTemplatePanel templatePanel) {
        super(templatePanel);
        this.templatePanel = templatePanel;
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        RenderConfig config = templatePanel.getFormData();

        if (StringUtils.isEmpty(config.getName())) {
            JOptionPane.showMessageDialog(templatePanel, "请输入模块名", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(config.getPkg())) {
            JOptionPane.showMessageDialog(templatePanel, "请输入包名", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(config.getTemplate())) {
            JOptionPane.showMessageDialog(templatePanel, "请选择模板目录", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(config.getOutput())) {
            JOptionPane.showMessageDialog(templatePanel, "请选择输出目录", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            Context.put(RenderConfig.class.getName(), config);

            List<TemplateFile> templateFileList = new ArrayList<TemplateFile>();

            TemplateHandler templateHandler = new TemplateHandler(new File(config.getTemplate()));
            templateHandler.listFile(templateHandler.getTempFolder(), templateFileList);

            TableConfig tableConfig = (TableConfig) Context.get(TableConfig.class.getName());
            List<Field> fieldList = (List) Context.get(Field.class.getName());

            RenderData renderData = new RenderData(config.getName(), config.getPkg(),
                    tableConfig.getTableName(), fieldList);

            //输出目录的根目录
            File outputRoot = new File(config.getOutput(), config.getPkg().replaceAll("\\.", "/"));

            if(!outputRoot.exists()) {
                outputRoot.mkdirs();
            }

            for(TemplateFile templateFile : templateFileList) {

                String fileName = renderData.getNameFirLetterUc() + templateFile.getName() + "." + templateFile.getType();

                File folder = new File(outputRoot, templateFile.getFolder());
                if(!folder.exists()) {
                    folder.mkdirs();
                }

                File outputFile = new File(folder, fileName);
                outputFile.createNewFile();

                templateHandler.applyTemplate(renderData, templateFile, outputFile);
            }

            JOptionPane.showMessageDialog(templatePanel, "代码生成成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
