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

package org.thorn.humpback.codebuilder.action;

import org.apache.commons.lang3.StringUtils;
import org.thorn.humpback.codebuilder.entity.DBConfig;
import org.thorn.humpback.codebuilder.service.DBOperator;
import org.thorn.humpback.codebuilder.view.DBConfigDialog;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 数据库配置界面按钮触发操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ConfigDataSourceAction extends AbsAction {

    private DBConfigDialog dbConfigDialog;

    public ConfigDataSourceAction(DBConfigDialog dbConfigDialog) {
        super(dbConfigDialog);

        this.dbConfigDialog = dbConfigDialog;
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        DBConfig dbConfig = dbConfigDialog.getFormData();

        if (StringUtils.isEmpty(dbConfig.getDriverClass())) {
            JOptionPane.showMessageDialog(dbConfigDialog, "请输入JDBC driver class", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(dbConfig.getUrl())) {
            JOptionPane.showMessageDialog(dbConfigDialog, "请输入JDBC url", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(dbConfig.getUsername())) {
            JOptionPane.showMessageDialog(dbConfigDialog, "请输入数据库用户名", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(dbConfig.getPassword())) {
            JOptionPane.showMessageDialog(dbConfigDialog, "请输入数据库密码", "错误", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                DBOperator dbOperator = new DBOperator(dbConfig);

                Context.put(DBConfig.class.getName(), dbConfig);
                Context.put(DBOperator.class.getName(), dbOperator);
            } catch (Exception ex) {
                throw new Exception("无法连接到数据库：" + ex.getMessage(), ex);
            }

            dbConfigDialog.setVisible(false);
        }

    }
}
