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

import org.thorn.common.gui.UIResourceUtils;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.localpass.action.ModifyPwdAction;

import javax.swing.*;
import java.awt.*;

/**
 * 修改密码弹出窗.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ModifyPwdDialog extends JDialog {

    private static Dimension labelDimension = new Dimension(80, 27);

    private static Dimension textDimension = new Dimension(220, 27);

    public ModifyPwdDialog() {
        super(Context.MAIN_FRAME, true);

        this.setBounds(UIResourceUtils.centerRectangle(Context.MAIN_FRAME.getBounds(), 320, 200));
        this.setTitle("修改密码");

        JPanel contentPanel = new JPanel();

        Box formBox = Box.createVerticalBox();
        formBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Box columnBox = Box.createHorizontalBox();
        JLabel label = new JLabel("当前密码：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        JPasswordField curPwdField = new JPasswordField();
        curPwdField.setPreferredSize(textDimension);
        columnBox.add(curPwdField);

        formBox.add(columnBox);
        formBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("新密码：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        JPasswordField newPwdField = new JPasswordField();
        newPwdField.setPreferredSize(textDimension);
        columnBox.add(newPwdField);

        formBox.add(columnBox);
        formBox.add(Box.createVerticalStrut(10));

        columnBox = Box.createHorizontalBox();
        label = new JLabel("重复新密码：");
        label.setPreferredSize(labelDimension);
        columnBox.add(label);

        JPasswordField repeatPwdField = new JPasswordField();
        repeatPwdField.setPreferredSize(textDimension);
        columnBox.add(repeatPwdField);

        formBox.add(columnBox);
        formBox.add(Box.createVerticalStrut(10));

        JButton button = new JButton("修改");
        button.addActionListener(new ModifyPwdAction(this, curPwdField, newPwdField, repeatPwdField));
        formBox.add(button);

        this.getRootPane().setDefaultButton(button);
        contentPanel.add(formBox);
        this.setContentPane(contentPanel);
        this.setResizable(false);
        this.setVisible(true);
    }
}
