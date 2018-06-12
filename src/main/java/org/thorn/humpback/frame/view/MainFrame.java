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

package org.thorn.humpback.frame.view;

import org.thorn.common.gui.UIResourceUtils;
import org.thorn.humpback.frame.service.Context;

import javax.swing.*;
import java.awt.*;

/**
 * 应用启动主界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class MainFrame extends JFrame {

    private JPanel contentPane;

    private JPanel bgPanel;

    public MainFrame() throws HeadlessException {
        super();

        Context.MAIN_FRAME = this;

        UIResourceUtils.setGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));

        this.setTitle("humpback");
        this.setIconImage(UIResourceUtils.getImageIcon("/icons/humpback3.png").getImage());
        this.setBounds(300, 100, 620, 500);

        this.contentPane = new JPanel();
        this.setContentPane(this.contentPane);

        JMenuBar menuBar = new TopMenuBar();
        this.setJMenuBar(menuBar);

        bgPanel = new LogoPanel();
        contentPane.add(bgPanel);
    }

    public void setMainPane(JPanel contentPane) {

        Context.clearSelectedTags();

        this.contentPane.removeAll();
        this.contentPane.add(contentPane);
        this.contentPane.updateUI();
    }

    public void removeMainPane() {
        Context.clearSelectedTags();

        this.contentPane.removeAll();
        contentPane.add(bgPanel);
        this.contentPane.updateUI();
    }
}
