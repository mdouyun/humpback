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

import javax.swing.*;
import java.awt.*;

/**
 * logo界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class LogoPanel extends JPanel {

    private Image image;

    public LogoPanel() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(580, 480));
        image=UIResourceUtils.getImageIcon("/icons/humpback2.png").getImage();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 10, 20, 550, 374, null);
        super.paint(g);
    }
}
