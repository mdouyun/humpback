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

import org.thorn.humpback.codebuilder.entity.Field;
import org.thorn.humpback.codebuilder.view.RenderTemplatePanel;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 字段映射界面下一步按钮对应的操作, 该操作保存字段映射配置并进入代码生成界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class MappingFieldAction extends AbsAction {

    private JTable table;

    public MappingFieldAction(JTable table) {
        super(Context.MAIN_FRAME);
        this.table = table;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        List<Field> fieldList = new ArrayList<Field>();

        TableModel tableModel = table.getModel();

        int rowSize = tableModel.getRowCount();

        for (int i = 0; i < rowSize; i++) {
            Field field = (Field) tableModel.getValueAt(i, 99);
            fieldList.add(field);
        }

        Context.put(Field.class.getName(), fieldList);

        JPanel panel = new RenderTemplatePanel();
        Context.MAIN_FRAME.setMainPane(panel);
    }
}
