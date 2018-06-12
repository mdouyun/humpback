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
package org.thorn.humpback.qrcode.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 二维码图片界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ImagePanel extends JPanel {


    private static final long serialVersionUID = 1L;

    private BufferedImage bi;

    public void setBufferedImage(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (bi != null) {
            g.drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), this);
        }
    }
}
