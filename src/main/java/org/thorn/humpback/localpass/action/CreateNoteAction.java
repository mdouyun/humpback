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

package org.thorn.humpback.localpass.action;

import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.frame.view.TopMenuBar;
import org.thorn.humpback.localpass.service.AccountService;
import org.thorn.humpback.localpass.service.LocationService;
import org.thorn.humpback.localpass.view.AccountTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * 创建密码本对应的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class CreateNoteAction extends AbsAction {

    private JTextField folder;

    private JTextField noteName;

    private JPasswordField passwordField;

    private JDialog noteDialog;

    public CreateNoteAction(JDialog noteDialog, JTextField folder,
                            JTextField noteName, JPasswordField passwordField) {
        super(noteDialog);
        this.noteDialog = noteDialog;
        this.folder = folder;
        this.noteName = noteName;
        this.passwordField = passwordField;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        String folderPath = folder.getText();
        String note = noteName.getText();
        String pwd = String.copyValueOf(passwordField.getPassword());

        if (StringUtils.isEmpty(folderPath)) {
            JOptionPane.showMessageDialog(noteDialog, "请选择目录", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(note)) {
            JOptionPane.showMessageDialog(noteDialog, "请输入密码本名称", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(pwd)) {
            JOptionPane.showMessageDialog(noteDialog, "请输入密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            AccountService accountService = SpringContext.getBean(AccountService.class);

            StringBuilder filePath = new StringBuilder(folderPath);
            if (!folderPath.endsWith("\\")) {
                filePath.append("\\").append(note);
            } else {
                filePath.append(note);
            }

            File noteFile = new File(filePath.toString());
            if (noteFile.exists()) {
                int result = JOptionPane.showConfirmDialog(noteDialog, "文件已经存在，是否覆盖？", "文件已存在", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            accountService.createNote(filePath.toString(), pwd);

            LocationService locationService = SpringContext.getBean(LocationService.class);
            locationService.addOpenedNote(filePath.toString());

            AccountTable accountTable = new AccountTable();
            Context.MAIN_FRAME.setMainPane(accountTable);

            noteDialog.setVisible(false);

            TopMenuBar topMenuBar = (TopMenuBar) Context.MAIN_FRAME.getJMenuBar();
            topMenuBar.addOperationMenus();
        }
    }
}
