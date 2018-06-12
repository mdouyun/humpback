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
import org.thorn.humpback.localpass.service.AccountService;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 密码修改对应操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ModifyPwdAction extends AbsAction {

    private JDialog modifyPwdDialog;

    private JPasswordField curPwdField;

    private JPasswordField newPwdField;

    private JPasswordField repeatPwdField;

    public ModifyPwdAction(JDialog modifyPwdDialog, JPasswordField curPwdField,
                           JPasswordField newPwdField, JPasswordField repeatPwdField) {
        super(modifyPwdDialog);
        this.modifyPwdDialog = modifyPwdDialog;
        this.curPwdField = curPwdField;
        this.newPwdField = newPwdField;
        this.repeatPwdField = repeatPwdField;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        String curPwd = String.copyValueOf(curPwdField.getPassword());
        String newPwd = String.copyValueOf(newPwdField.getPassword());
        String repeatPwd = String.copyValueOf(repeatPwdField.getPassword());

        if (StringUtils.isEmpty(curPwd)) {
            JOptionPane.showMessageDialog(modifyPwdDialog, "请输入当前密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(newPwd)) {
            JOptionPane.showMessageDialog(modifyPwdDialog, "请输入新密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(repeatPwd)) {
            JOptionPane.showMessageDialog(modifyPwdDialog, "请再次输入新密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (!StringUtils.equals(newPwd, repeatPwd)) {
            JOptionPane.showMessageDialog(modifyPwdDialog, "两次输入的新密码不一样", "错误", JOptionPane.WARNING_MESSAGE);
        } else {

            AccountService accountService = SpringContext.getBean(AccountService.class);

            if (accountService.verifyPassword(curPwd)) {
                accountService.modifyPassword(newPwd);

                modifyPwdDialog.setVisible(false);
                JOptionPane.showMessageDialog(Context.MAIN_FRAME, "密码修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modifyPwdDialog, "当前密码错误", "错误", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
