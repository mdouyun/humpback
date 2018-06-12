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

package org.thorn.humpback.localpass.view;

import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.common.gui.UIResourceUtils;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.localpass.action.LoadNoteAction;
import org.thorn.humpback.localpass.service.LocationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * 选择密码本弹出窗.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class OpenNoteDialog extends JDialog {

    public OpenNoteDialog(final String filePath) {
        super(Context.MAIN_FRAME, true);
        this.setBounds(UIResourceUtils.centerRectangle(Context.MAIN_FRAME.getBounds(), 270, 190));

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("文件地址：");
        labelPanel.add(label);
        contentPanel.add(labelPanel);

        final JTextField file = new JTextField();
        file.setEditable(false);
        final JFileChooser fileChooser = new JFileChooser();
        final JDialog thisDialog = this;
        if (StringUtils.isNotBlank(filePath)) {
            file.setText(filePath);
            fileChooser.setCurrentDirectory(new File(filePath).getParentFile());
        } else {
            LocationService locationService = SpringContext.getBean(LocationService.class);
            String folderPath = locationService.getNotesSaveFolder();
            fileChooser.setCurrentDirectory(new File(folderPath));
        }
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("选择密码本");
        fileChooser.setDialogTitle("请选择密码本");

        JButton fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(thisDialog);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = fileChooser.getSelectedFile();
                    file.setText(data.getAbsolutePath());
                }
            }
        });

        Box columnBox = Box.createHorizontalBox();
        columnBox.add(file);
        columnBox.add(Box.createHorizontalStrut(5));
        columnBox.add(fileBtn);
        contentPanel.add(columnBox);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("密码：");
        labelPanel.add(label);
        contentPanel.add(labelPanel);

        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(passwordField.getWidth(), 27));
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(10));

        JButton button = new JButton();

        this.setTitle("打开密码本");
        button.setText("打开");
        button.addActionListener(new LoadNoteAction(this, file, passwordField));
        contentPanel.add(button);
        this.getRootPane().setDefaultButton(button);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                passwordField.requestFocus();
            }
        });

        this.setContentPane(contentPanel);
        this.setResizable(false);
        this.setVisible(true);
    }

}
