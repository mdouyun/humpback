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

package org.thorn.humpback.qrcode.view;

import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.service.WinRegistry;
import org.thorn.humpback.qrcode.action.QrGenerateAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 二维码生成主界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class QrPanel extends JPanel {

    private JTextField imageFolder;

    private final static Dimension LabelDimension = new Dimension(85, 25);

    private final static Dimension fieldDimension = new Dimension(250, 27);

    public QrPanel() {
        this.setPreferredSize(new Dimension(620, 490));
        this.setBorder(BorderFactory.createEmptyBorder(10, 230, 2, 230));

        WinRegistry registry = SpringContext.getBean(WinRegistry.class);
        String dir = registry.getLocation();

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("图片目录：");
        label.setPreferredSize(LabelDimension);
        formPanel.add(label);

        imageFolder = new JTextField(dir);
        imageFolder.setPreferredSize(fieldDimension);
        imageFolder.setEditable(false);

        final JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("选择目录");
        folderChooser.setDialogTitle("请选择二维图片生成目录");
        folderChooser.setCurrentDirectory(new File(dir));

        final JPanel thisPanel = this;
        JButton fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = folderChooser.showOpenDialog(thisPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = folderChooser.getSelectedFile();
                    imageFolder.setText(data.getAbsolutePath());
                }
            }
        });

        formPanel.add(imageFolder);
        formPanel.add(fileBtn);

        this.add(formPanel);
        formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        label = new JLabel("二维码内容：");
        label.setPreferredSize(LabelDimension);
        formPanel.add(label);

        JTextField strField = new JTextField();
        strField.setPreferredSize(fieldDimension);

        formPanel.add(label);
        formPanel.add(strField);
        formPanel.add(Box.createHorizontalStrut(60));

        this.add(formPanel);
        this.add(Box.createVerticalStrut(40));
        Box rowBox = Box.createHorizontalBox();

        JButton button = new JButton("生成二维码");
        rowBox.add(Box.createHorizontalStrut(100));
        rowBox.add(button);
        rowBox.add(Box.createHorizontalStrut(100));

        this.add(rowBox);
        this.add(Box.createVerticalStrut(40));

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(275, 275));

        button.addActionListener(new QrGenerateAction(imagePanel, strField, imageFolder));
        this.add(imagePanel);
    }
}
