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

package org.thorn.humpback.codebuilder.view;

import org.thorn.common.SpringContext;
import org.thorn.humpback.codebuilder.action.RenderTemplateAction;
import org.thorn.humpback.codebuilder.action.RetMappingFieldAction;
import org.thorn.humpback.codebuilder.entity.RenderConfig;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.frame.service.WinRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 配置模板文件及代码生成目录的界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class RenderTemplatePanel extends JPanel {

    private JTextField moduleField;

    private JTextField pkgField;

    private JTextField outputField;

    private JTextField templateField;

    private static Dimension labelDimension = new Dimension(80, 27);

    public RenderTemplatePanel() throws IOException {
        this.setPreferredSize(new Dimension(580, 420));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "第三步 配置代码模板"));

        WinRegistry registry = SpringContext.getBean(WinRegistry.class);
        String dir = registry.getLocation();
        final JPanel thisPanel = this;

        Box rowBox = Box.createVerticalBox();
        Box columnBox = Box.createHorizontalBox();

        JEditorPane htmlPane = new JEditorPane();
        htmlPane.setAlignmentX(JEditorPane.LEFT_ALIGNMENT);
        htmlPane.setEditable(false);
        htmlPane.setPage(this.getClass().getResource("/tag.html"));

        JScrollPane scrollPane = new JScrollPane(htmlPane);
        scrollPane.setPreferredSize(new Dimension(520, 180));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        columnBox.add(scrollPane);
        rowBox.add(columnBox);

        rowBox.add(Box.createVerticalStrut(20));
        columnBox = Box.createHorizontalBox();

        JLabel label = new JLabel("模块名：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        moduleField = new JTextField();
        moduleField.setPreferredSize(new Dimension(120, 27));
        columnBox.add(moduleField);
        columnBox.add(Box.createHorizontalStrut(50));

        label = new JLabel("包名：");
        label.setPreferredSize(new Dimension(50, 27));
        columnBox.add(label);

        pkgField = new JTextField();
        pkgField.setPreferredSize(new Dimension(200, 27));
        columnBox.add(pkgField);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(20));
        columnBox = Box.createHorizontalBox();

        label = new JLabel("模板文件夹：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        templateField = new JTextField();
        templateField.setEditable(false);

        final JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("选择目录");
        folderChooser.setDialogTitle("请选择模板目录");
        folderChooser.setCurrentDirectory(new File(dir));
        JButton fileBtn = new JButton("选择");

        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = folderChooser.showOpenDialog(thisPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = folderChooser.getSelectedFile();
                    templateField.setText(data.getAbsolutePath());
                }
            }
        });

        columnBox.add(templateField);
        columnBox.add(Box.createHorizontalStrut(5));
        columnBox.add(fileBtn);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(20));
        columnBox = Box.createHorizontalBox();

        label = new JLabel("输出目录：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        outputField = new JTextField();
        outputField.setEditable(false);

        final JFileChooser folderChooser1 = new JFileChooser();
        folderChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser1.setApproveButtonText("选择目录");
        folderChooser1.setDialogTitle("请选择生成目录");
        folderChooser1.setCurrentDirectory(new File(dir));

        fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = folderChooser1.showOpenDialog(thisPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = folderChooser1.getSelectedFile();
                    outputField.setText(data.getAbsolutePath());
                }
            }
        });

        columnBox.add(outputField);
        columnBox.add(Box.createHorizontalStrut(5));
        columnBox.add(fileBtn);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(30));
        columnBox = Box.createHorizontalBox();

        JButton button = new JButton("上一步");
        button.addActionListener(new RetMappingFieldAction());
        columnBox.add(button);
        columnBox.add(Box.createHorizontalStrut(30));
        button = new JButton("完成");
        button.addActionListener(new RenderTemplateAction(this));
        columnBox.add(button);

        rowBox.add(columnBox);
        this.add(rowBox);

        RenderConfig config = (RenderConfig) Context.get(RenderConfig.class.getName());
        if(config != null) {
            moduleField.setText(config.getName());
            pkgField.setText(config.getPkg());
            outputField.setText(config.getOutput());
            templateField.setText(config.getTemplate());
        }
    }

    public RenderConfig getFormData() {
        RenderConfig config = new RenderConfig();

        config.setName(moduleField.getText());
        config.setOutput(outputField.getText());
        config.setPkg(pkgField.getText());
        config.setTemplate(templateField.getText());

        return config;
    }
}
