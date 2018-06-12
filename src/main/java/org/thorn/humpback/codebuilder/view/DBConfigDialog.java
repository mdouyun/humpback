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

import org.thorn.common.gui.UIResourceUtils;
import org.thorn.humpback.codebuilder.action.ConfigDataSourceAction;
import org.thorn.humpback.codebuilder.entity.DBConfig;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.*;

/**
 * 数据库配置对话框.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class DBConfigDialog extends JDialog {

    private JTextField driverField;

    private JTextField urlField;

    private JTextField userField;

    private JTextField passwordField;

    private static Dimension labelDimension = new Dimension(70, 27);

    private static Dimension textDimension = new Dimension(200, 27);

    public DBConfigDialog() {
        super(Context.MAIN_FRAME, true);

        this.setBounds(UIResourceUtils.centerRectangle(Context.MAIN_FRAME.getBounds(), 320, 250));
        this.setTitle("数据库配置");

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        Box rowBox = Box.createVerticalBox();

        Box columnBox = Box.createHorizontalBox();
        JLabel label = new JLabel("Driver:");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        driverField = new JTextField();
        driverField.setPreferredSize(textDimension);
        columnBox.add(driverField);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("Url:");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        urlField = new JTextField();
        urlField.setPreferredSize(textDimension);
        columnBox.add(urlField);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("Username:");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        userField = new JTextField();
        userField.setPreferredSize(textDimension);
        columnBox.add(userField);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("Password:");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        passwordField = new JTextField();
        passwordField.setPreferredSize(textDimension);
        columnBox.add(passwordField);

        rowBox.add(columnBox);
        rowBox.add(Box.createVerticalStrut(10));

        JButton button = new JButton("确定");
        button.addActionListener(new ConfigDataSourceAction(this));
        rowBox.add(button);

        this.getRootPane().setDefaultButton(button);

        contentPanel.add(rowBox);

        DBConfig dbConfig = (DBConfig) Context.get(DBConfig.class.getName());
        if(dbConfig != null) {
            driverField.setText(dbConfig.getDriverClass());
            urlField.setText(dbConfig.getUrl());
            userField.setText(dbConfig.getUsername());
            passwordField.setText(dbConfig.getPassword());
        }

        this.setContentPane(contentPanel);
        this.setResizable(false);
        this.setVisible(true);
    }

    public DBConfig getFormData() {
        DBConfig dbConfig = new DBConfig();

        dbConfig.setDriverClass(driverField.getText());
        dbConfig.setUrl(urlField.getText());
        dbConfig.setUsername(userField.getText());
        dbConfig.setPassword(passwordField.getText());

        return dbConfig;
    }

}
