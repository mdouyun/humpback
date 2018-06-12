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

import org.thorn.humpback.codebuilder.view.ExecuteSqlPanel;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.frame.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 字段映射界面上一步按钮对应的操作, 该操作返回到数据库脚本配置界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class RetExecuteSqlAction extends AbsAction {

    public RetExecuteSqlAction() {
        super(Context.MAIN_FRAME);
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        MainFrame mainFrame = Context.MAIN_FRAME;

        JPanel panel = new ExecuteSqlPanel();
        mainFrame.setMainPane(panel);
    }
}
