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
import org.thorn.humpback.codebuilder.view.RenderTemplatePanel;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 菜单栏选择代码生成器所执行的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class LoadCodeBuilderPanelAction extends AbsAction {

    public LoadCodeBuilderPanelAction() {
        super(Context.MAIN_FRAME);
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        JPanel panel = new ExecuteSqlPanel();
//        JPanel panel = new RenderTemplatePanel();
        Context.MAIN_FRAME.setMainPane(panel);
    }
}
