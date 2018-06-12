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
import org.thorn.humpback.localpass.entity.Account;
import org.thorn.humpback.localpass.enums.AccountOperateEnum;
import org.thorn.humpback.localpass.service.AccountService;
import org.thorn.humpback.localpass.view.AccountDialog;
import org.thorn.humpback.localpass.view.AccountTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 账户增删改对应的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class AccountOperateAction extends AbsAction {

    private AccountDialog dialog;

    private AccountOperateEnum operateEnum;

    public AccountOperateAction(AccountDialog dialog, AccountOperateEnum operateEnum) {
        super(dialog);
        this.dialog = dialog;
        this.operateEnum = operateEnum;
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        Account account = dialog.getFormData();

        if (StringUtils.isEmpty(account.getAddress()) && operateEnum != AccountOperateEnum.DELETE) {
            JOptionPane.showMessageDialog(dialog, "请输入地址", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(account.getUsername()) && operateEnum != AccountOperateEnum.DELETE) {
            JOptionPane.showMessageDialog(dialog, "请输入账号", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(account.getPassword()) && operateEnum != AccountOperateEnum.DELETE) {
            JOptionPane.showMessageDialog(dialog, "请输入密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            AccountService accountService = SpringContext.getBean(AccountService.class);

            if (operateEnum == AccountOperateEnum.DELETE) {
                accountService.deleteAccount(account);
            } else if (operateEnum == AccountOperateEnum.MODIFY) {
                accountService.modifyAccount(account);
            } else {
                accountService.addAccount(account);
            }

            dialog.setVisible(false);

            AccountTable accountTable = new AccountTable();
            Context.MAIN_FRAME.setMainPane(accountTable);
        }

    }
}
