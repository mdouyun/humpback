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

import org.thorn.common.SpringContext;
import org.thorn.humpback.localpass.entity.Account;
import org.thorn.humpback.localpass.service.AccountService;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Set;

/**
 * 账户列表数据模型.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class AccountTableModal extends AbstractTableModel {

    private static final String[] HEADER = {"网站地址", "账号", "标签"};

    private List<Account> data;

    public AccountTableModal(Set<String> tags) throws Exception {

        AccountService accountService = SpringContext.getBean(AccountService.class);
        data = accountService.queryAccounts(tags);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return HEADER.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = data.get(rowIndex);

        Object value = null;

        switch (columnIndex) {
            case 0:
                value = account.getAddress();
                break;
            case 1:
                value = account.getUsername();
                break;
            case 2:
                Set<String> tags = account.getTag();
                StringBuilder stringBuilder = new StringBuilder();
                for (String tag : tags) {
                    stringBuilder.append(tag).append("#");
                }

                value = stringBuilder.toString();
                break;
            case 3:
                value = account.getRemark();
                break;

            default:
                value = account.getId();
                break;
        }

        return value;
    }

    @Override
    public String getColumnName(int column) {
        return HEADER[column];
    }

}
