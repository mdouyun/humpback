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

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 统一的弹出对话框按钮操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class OpenDialogAction extends AbsAction {

    public OpenDialogAction() {
        super(Context.MAIN_FRAME);
    }

    public OpenDialogAction(Component parentComp) {
        super(parentComp);
    }

    @Override
    public void action(ActionEvent e) throws Exception {
        String command = e.getActionCommand();

        Class cls = Class.forName(command);
        cls.newInstance();

/*        if(StringUtils.equals(command, AccountDialog.class.getName())) {
            new AccountDialog();
        } else if(StringUtils.equals(command, NotesFolderSettingDialog.class.getName())) {
            new NotesFolderSettingDialog();
        } else if(StringUtils.equals(command, ModifyPwdDialog.class.getName())) {
            new ModifyPwdDialog();
        } else if(StringUtils.equals(command, DBConfigDialog.class.getName())) {
            new DBConfigDialog();
        }*/
    }
}
