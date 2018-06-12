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
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.localpass.service.LocationService;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 设置密码本目录操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class SettingNotesFolderAction extends AbsAction {

    private JTextField folder;

    private JDialog settingDialog;

    public SettingNotesFolderAction(JDialog settingDialog, JTextField folder) {
        super(settingDialog);
        this.folder = folder;
        this.settingDialog = settingDialog;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        String folderPath = folder.getText();

        if (StringUtils.isEmpty(folderPath)) {
            JOptionPane.showMessageDialog(settingDialog, "请选择目录", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            LocationService locationService = SpringContext.getBean(LocationService.class);
            locationService.setNotesSaveFolder(folderPath);

            settingDialog.setVisible(false);
        }

    }
}
