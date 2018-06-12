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

import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 退出按钮对应操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ExitAction extends AbsAction {


    public ExitAction() {
        super(Context.MAIN_FRAME);
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        int result = JOptionPane.showConfirmDialog(super.parentComp, "是否确认退出？", "退出", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
