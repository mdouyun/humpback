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

import org.thorn.common.SpringContext;
import org.thorn.humpback.localpass.entity.Account;
import org.thorn.humpback.localpass.service.AccountService;
import org.thorn.humpback.localpass.view.AccountDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 密码本列表双击操作, 账号编辑操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class OpenAccountDialogListener extends MouseAdapter {

    private JTable table;

    public OpenAccountDialogListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2) {
            int idNum = 4;
            int rowNum = table.getSelectedRow();

            String id = (String) table.getModel().getValueAt(rowNum, idNum);

            AccountService accountService = SpringContext.getBean(AccountService.class);

            Account account = accountService.queryAccount(id);

            AccountDialog dialog = new AccountDialog(account);
        }
    }


}
