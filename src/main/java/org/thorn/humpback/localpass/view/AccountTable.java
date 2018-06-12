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
import org.thorn.humpback.localpass.action.OpenAccountDialogListener;
import org.thorn.humpback.localpass.action.TagSearchAction;
import org.thorn.humpback.localpass.service.AccountService;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Set;

/**
 * 账户列表界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class AccountTable extends JPanel {

    private JTable table;

    public AccountTable() throws Exception {

        JPanel queryPanel = new JPanel();
        queryPanel.setBorder(BorderFactory.createTitledBorder("根据标签查询"));

        AccountService accountService = SpringContext.getBean(AccountService.class);
        Set<String> tagSet = accountService.queryTags();

        if (tagSet != null && tagSet.size() > 0) {

            JPanel tagPanel = new JPanel();
            tagPanel.setPreferredSize(new Dimension(570, 60));
            tagPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            for (String tag : tagSet) {
                JCheckBox checkBox = new JCheckBox(tag);
                checkBox.addItemListener(new TagSearchAction(this));
                tagPanel.add(checkBox);
            }

            queryPanel.add(tagPanel);
        }

        table = new JTable(new AccountTableModal(null));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
        table.setRowHeight(22);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        // set table double click
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.addMouseListener(new OpenAccountDialogListener(table));
        JScrollPane tableScrollPanel = new JScrollPane(table);
        tableScrollPanel.setPreferredSize(new Dimension(590, 300));

        Box rowBox = Box.createVerticalBox();
        JScrollPane jScrollPane = new JScrollPane(queryPanel);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rowBox.add(jScrollPane);
        rowBox.add(Box.createVerticalStrut(5));
        rowBox.add(tableScrollPanel);

        this.add(rowBox);
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    public void query(Set<String> tags) throws Exception {
        table.setModel(new AccountTableModal(tags));
    }
}
