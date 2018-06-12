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

/**
 * 加载密码本对应操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class LoadNoteAction extends AbsAction {

    private JTextField file;

    private JPasswordField passwordField;

    private JDialog noteDialog;

    public LoadNoteAction(JDialog noteDialog, JTextField file, JPasswordField passwordField) {
        super(noteDialog);
        this.noteDialog = noteDialog;
        this.file = file;
        this.passwordField = passwordField;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        String filePath = file.getText();
        String pwd = String.copyValueOf(passwordField.getPassword());

        if (StringUtils.isEmpty(filePath)) {
            JOptionPane.showMessageDialog(noteDialog, "请选择密码本", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            if (StringUtils.isEmpty(pwd)) {
                JOptionPane.showMessageDialog(noteDialog, "请输入密码", "错误", JOptionPane.WARNING_MESSAGE);
            } else {
                AccountService accountService = SpringContext.getBean(AccountService.class);
                accountService.loadNote(filePath, pwd);

                LocationService locationService = SpringContext.getBean(LocationService.class);
                locationService.addOpenedNote(filePath);

                AccountTable accountTable = new AccountTable();
                Context.MAIN_FRAME.setMainPane(accountTable);

                noteDialog.setVisible(false);

                TopMenuBar topMenuBar = (TopMenuBar) Context.MAIN_FRAME.getJMenuBar();
                topMenuBar.addOperationMenus();
            }
        }
    }
}
