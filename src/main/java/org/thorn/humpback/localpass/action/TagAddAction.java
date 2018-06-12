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

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 账户增改界面, 标签操作监听器.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class TagAddAction implements ItemListener {

    private JTextField textField;

    public TagAddAction(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        JCheckBox checkBox = (JCheckBox) e.getSource();

        String tags = textField.getText();
        int index = StringUtils.indexOf(tags, checkBox.getText());

        if (e.getStateChange() == ItemEvent.SELECTED && index <= -1) {
            textField.setText(tags + checkBox.getText() + "#");
        } else if (e.getStateChange() == ItemEvent.DESELECTED && index >= 0) {

            tags = StringUtils.replace(tags, checkBox.getText() + "#", "");
            textField.setText(tags);
        }
    }

}
