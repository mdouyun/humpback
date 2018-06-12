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

import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.frame.view.MainFrame;
import org.thorn.humpback.frame.view.TopMenuBar;
import org.thorn.humpback.localpass.service.AccountService;


import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 注销密码本当前用户.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class LogoutAction extends AbsAction {

    public LogoutAction() {
        super(Context.MAIN_FRAME);
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        int result = JOptionPane.showConfirmDialog(super.parentComp, "是否确认注销？", "注销", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {

            AccountService accountService = SpringContext.getBean(AccountService.class);
            accountService.logoout();

            MainFrame frame = Context.MAIN_FRAME;

            TopMenuBar topMenuBar = (TopMenuBar) Context.MAIN_FRAME.getJMenuBar();
            topMenuBar.removeOperationMenus();
            frame.removeMainPane();
        }
    }
}
