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

package org.thorn.humpback.frame.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 对{@link ActionListener}进行了简单实现, 对异常作了统一处理.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsAction implements ActionListener {

    static Logger log = LoggerFactory.getLogger(AbsAction.class);

    protected Component parentComp;

    public AbsAction(Component parentComp) {
        this.parentComp = parentComp;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            action(e);
        } catch (Exception ex) {
            log.error("The button name : " + e.getActionCommand() + " and panel cls : " + e.getSource().getClass(), ex);
            String msg = ex.getMessage().replaceAll(";", ";\n");
            JOptionPane.showMessageDialog(parentComp, msg, "操作异常", JOptionPane.ERROR_MESSAGE);
        }
    }

    public abstract void action(ActionEvent e) throws Exception;

}
