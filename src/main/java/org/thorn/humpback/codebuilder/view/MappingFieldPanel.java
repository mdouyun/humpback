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

import org.thorn.humpback.codebuilder.action.MappingFieldAction;
import org.thorn.humpback.codebuilder.action.MappingModalListener;
import org.thorn.humpback.codebuilder.action.RetExecuteSqlAction;
import org.thorn.humpback.codebuilder.entity.JDBCTypesMapping;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * 配置数据字段与java对象属性的界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class MappingFieldPanel extends JPanel {

    public MappingFieldPanel() {
        this.setPreferredSize(new Dimension(580, 420));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "第二步 配置字段映射"));

        FieldMappingModal mappingModal = new FieldMappingModal();
        JTable table = new JTable(mappingModal);
        table.getModel().addTableModelListener(new MappingModalListener(table));

        TableColumnModel tableColumnModel = table.getColumnModel();
        TableColumn tableColumn = tableColumnModel.getColumn(4);

        JComboBox comboBox = new JComboBox(JDBCTypesMapping.JAVA_TYPES);
        DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
        tableColumn.setCellEditor(cellEditor);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
        table.setRowHeight(22);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        JScrollPane tableScrollPanel = new JScrollPane(table);
        tableScrollPanel.setPreferredSize(new Dimension(560, 330));

        Box rowBox = Box.createVerticalBox();
        rowBox.add(tableScrollPanel);

        rowBox.add(Box.createVerticalStrut(10));

        Box columnBox = Box.createHorizontalBox();
        JButton button = new JButton("上一步");
        button.addActionListener(new RetExecuteSqlAction());
        columnBox.add(button);
        columnBox.add(Box.createHorizontalStrut(30));
        button = new JButton("下一步");
        button.addActionListener(new MappingFieldAction(table));
        columnBox.add(button);

        rowBox.add(columnBox);
        this.add(rowBox);
    }
}
