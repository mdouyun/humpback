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
import org.thorn.humpback.localpass.action.SettingNotesFolderAction;
import org.thorn.humpback.localpass.service.LocationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 设置密码本存放地址.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class NotesFolderSettingDialog extends JDialog {

    public NotesFolderSettingDialog() {
        super(Context.MAIN_FRAME, true);

        LocationService locationService = SpringContext.getBean(LocationService.class);
        String folderPath = locationService.getNotesSaveFolder();

        this.setBounds(UIResourceUtils.centerRectangle(Context.MAIN_FRAME.getBounds(), 280, 150));

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("密码本存放目录：");
        labelPanel.add(label);
        contentPanel.add(labelPanel);

        final JTextField folder = new JTextField();
        folder.setEditable(false);
        final JFileChooser fileChooser = new JFileChooser();
        final JDialog thisDialog = this;
        if (StringUtils.isNotBlank(folderPath)) {
            folder.setText(folderPath);
            fileChooser.setCurrentDirectory(new File(folderPath));
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("选择目录");
        fileChooser.setDialogTitle("请选择密码本存放目录");

        JButton fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(thisDialog);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = fileChooser.getSelectedFile();
                    folder.setText(data.getAbsolutePath());
                }
            }
        });

        Box columnBox = Box.createHorizontalBox();
        columnBox.add(folder);
        columnBox.add(Box.createHorizontalStrut(5));
        columnBox.add(fileBtn);
        contentPanel.add(columnBox);
        contentPanel.add(Box.createVerticalStrut(20));

        JButton button = new JButton();
        this.setTitle("设置密码本存放目录");
        button.setText("确定");
        button.addActionListener(new SettingNotesFolderAction(this, folder));
        this.getRootPane().setDefaultButton(button);
        columnBox = Box.createHorizontalBox();
        columnBox.add(button);
        columnBox.add(Box.createHorizontalStrut(10));
        contentPanel.add(columnBox);

        this.setContentPane(contentPanel);
        this.setResizable(false);
        this.setVisible(true);
    }
}
