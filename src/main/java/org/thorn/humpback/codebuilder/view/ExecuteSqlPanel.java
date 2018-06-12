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

package org.thorn.humpback.codebuilder.view;

import org.thorn.humpback.codebuilder.action.ExecuteSqlAction;
import org.thorn.humpback.codebuilder.entity.TableConfig;
import org.thorn.humpback.frame.action.OpenDialogAction;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.*;

/**
 * 配置数据库脚本的界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ExecuteSqlPanel extends JPanel {

    private JTextArea sqlArea;

    private JCheckBox isOverrideTableCheckBox;

    private JTextField tableNameField;

    public ExecuteSqlPanel() {
        this.setPreferredSize(new Dimension(580, 420));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "第一步 连接数据库并创建表"));

        Box rowBox = Box.createVerticalBox();
        Box columnBox = Box.createHorizontalBox();

        isOverrideTableCheckBox = new JCheckBox("允许覆盖表");
        columnBox.add(isOverrideTableCheckBox);
        columnBox.add(Box.createHorizontalStrut(40));

        JLabel label = new JLabel("表名：");
        tableNameField = new JTextField();
        columnBox.add(label);
        columnBox.add(tableNameField);
        columnBox.add(Box.createHorizontalStrut(40));

        JButton button = new JButton("配置数据库");
        button.setActionCommand(DBConfigDialog.class.getName());
        button.addActionListener(new OpenDialogAction());
        columnBox.add(button);
        columnBox.add(Box.createHorizontalStrut(60));

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("建表SQL：");
        columnBox.add(label);
        columnBox.add(Box.createHorizontalStrut(480));

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));
        columnBox = Box.createHorizontalBox();

        sqlArea = new JTextArea(15, 45);
        sqlArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(sqlArea);
        columnBox.add(scrollPane);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        button = new JButton("下一步");
        button.addActionListener(new ExecuteSqlAction(this));
        rowBox.add(button);

        this.add(rowBox);

        TableConfig tableConfig = (TableConfig) Context.get(TableConfig.class.getName());
        if(tableConfig != null) {
            sqlArea.setText(tableConfig.getSql());
            tableNameField.setText(tableConfig.getTableName());
            isOverrideTableCheckBox.setSelected(tableConfig.getOverride());
        }
    }

    public TableConfig getFormData() {

        return new TableConfig(sqlArea.getText(),
                tableNameField.getText(), isOverrideTableCheckBox.isSelected());
    }

}
