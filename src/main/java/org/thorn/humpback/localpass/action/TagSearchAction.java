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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.localpass.view.AccountTable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 账号列表界面, 标签搜索操作监听器.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class TagSearchAction implements ItemListener {

    static Logger log = LoggerFactory.getLogger(TagSearchAction.class);

    private AccountTable accountTable;

    public TagSearchAction(AccountTable accountTable) {
        this.accountTable = accountTable;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();

        if(e.getStateChange() == ItemEvent.SELECTED) {
            Context.addSelectedTags(checkBox.getText());
        } else {
            Context.removeSelectedTags(checkBox.getText());
        }

        // do query
        try {
            accountTable.query(Context.getSelectedTags());
        } catch (Exception ex) {
            log.error("The button name : " + checkBox.getText() + " and panel cls : " + e.getSource().getClass(), ex);
            String msg = ex.getMessage().replaceAll(";", ";\n");
            JOptionPane.showMessageDialog(Context.MAIN_FRAME, msg, "操作异常", JOptionPane.ERROR_MESSAGE);
        }
    }
}
