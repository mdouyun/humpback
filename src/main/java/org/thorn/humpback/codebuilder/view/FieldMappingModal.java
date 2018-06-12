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

import org.thorn.humpback.codebuilder.entity.Field;
import org.thorn.humpback.frame.service.Context;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 数据映射列表的数据模型.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class FieldMappingModal extends AbstractTableModel {

    private static final String[] HEADER = {"列名", "列类型", "主键", "字段名", "字段类型"};

    private List<Field> fieldList;

    public FieldMappingModal() {
        super();
        fieldList = (List<Field>) Context.get(Field.class.getName());
    }

    @Override
    public int getRowCount() {
        return fieldList.size();
    }

    @Override
    public int getColumnCount() {
        return HEADER.length;
    }

    @Override
    public String getColumnName(int column) {
        return HEADER[column];
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        Field field = fieldList.get(row);
        if(column == 3) {
            field.setFieldName((String) aValue);
        } else if(column == 4) {
            field.setFieldType((String) aValue);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 2;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Field field = fieldList.get(rowIndex);

        Object value = null;

        switch (columnIndex) {
            case 0 :
                value = field.getTabName();
                break;
            case 1 :
                value = field.getTabType();
                break;
            case 2 :
                value = field.isKey();
                break;
            case 3 :
                value = field.getFieldName();
                break;
            case 4 :
                value = field.getFieldType();
                break;

            default:
                value = field;
                break;
        }


        return value;
    }
}
