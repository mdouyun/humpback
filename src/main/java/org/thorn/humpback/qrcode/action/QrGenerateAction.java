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

package org.thorn.humpback.qrcode.action;

import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.frame.service.Context;
import org.thorn.humpback.qrcode.service.QrGenerator;
import org.thorn.humpback.qrcode.view.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * 二维码生成按钮对应的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class QrGenerateAction extends AbsAction {

    private static final String DEFAULT_IMAGE_NAME = "qrcode.png";

    private ImagePanel imagePanel;

    private JTextField contextField;

    private JTextField folderField;

    public QrGenerateAction(ImagePanel imagePanel, JTextField contextField, JTextField folderField) {
        super(Context.MAIN_FRAME);
        this.imagePanel = imagePanel;
        this.contextField = contextField;
        this.folderField = folderField;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        String content = contextField.getText();
        String folder = folderField.getText();

        if (StringUtils.isEmpty(folder)) {
            JOptionPane.showMessageDialog(Context.MAIN_FRAME, "请选择要存放图片的目录", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (StringUtils.isEmpty(content)) {
            JOptionPane.showMessageDialog(Context.MAIN_FRAME, "请输入二维码内容", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            if (!folder.endsWith("\\")) {
                folder += "\\";
            }

            QrGenerator generator = SpringContext.getBean(QrGenerator.class);
            BufferedImage bi = generator.generate(content, folder + DEFAULT_IMAGE_NAME);
            imagePanel.setBufferedImage(bi);
            imagePanel.repaint();
        }

    }
}
