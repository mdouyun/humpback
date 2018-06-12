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
import org.thorn.humpback.codebuilder.entity.Field;
import org.thorn.humpback.codebuilder.entity.TableConfig;
import org.thorn.humpback.codebuilder.service.DBOperator;
import org.thorn.humpback.codebuilder.view.ExecuteSqlPanel;
import org.thorn.humpback.codebuilder.view.MappingFieldPanel;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.frame.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * 填写数据库脚本界面下一步按钮对应的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ExecuteSqlAction extends AbsAction {

    private ExecuteSqlPanel executeSqlPanel;

    public ExecuteSqlAction(ExecuteSqlPanel executeSqlPanel) {
        super(executeSqlPanel);
        this.executeSqlPanel = executeSqlPanel;
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        TableConfig tableConfig = executeSqlPanel.getFormData();

        DBConfig dbConfig = (DBConfig) Context.get(DBConfig.class.getName());

        if (StringUtils.isEmpty(tableConfig.getTableName())) {
            JOptionPane.showMessageDialog(executeSqlPanel, "请输入数据库表名", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(tableConfig.getSql())) {
            JOptionPane.showMessageDialog(executeSqlPanel, "请输入创建表的SQL", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (dbConfig == null) {
            JOptionPane.showMessageDialog(executeSqlPanel, "请配置数据库", "错误", JOptionPane.WARNING_MESSAGE);
        }  else {

            DBOperator dbOperator = (DBOperator) Context.get(DBOperator.class.getName());

            boolean isTableExist = dbOperator.isTableExist(tableConfig.getTableName());
            if(isTableExist && !tableConfig.getOverride()) {
                JOptionPane.showMessageDialog(executeSqlPanel, "数据库表已经存在", "错误", JOptionPane.WARNING_MESSAGE);
                return ;
            }

            if(isTableExist) {
                dbOperator.dropTable(tableConfig.getTableName());
            }

            dbOperator.executeSql(tableConfig.getSql());

            List<Field> fieldList = dbOperator.queryDBField(tableConfig.getTableName());

            Context.put(TableConfig.class.getName(), tableConfig);
            Context.put(Field.class.getName(), fieldList);

            MainFrame frame = Context.MAIN_FRAME;
            JPanel mappingPanel = new MappingFieldPanel();
            frame.setMainPane(mappingPanel);
        }
    }
}
