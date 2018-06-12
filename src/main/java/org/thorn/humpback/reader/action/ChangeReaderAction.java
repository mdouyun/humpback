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

package org.thorn.humpback.reader.action;

import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.action.AbsAction;
import org.thorn.humpback.reader.entity.PdfConfiguration;
import org.thorn.humpback.reader.service.PdfReader;
import org.thorn.humpback.reader.view.ReaderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * 电子书转换按钮对应操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ChangeReaderAction extends AbsAction {

    private ReaderPanel readerPanel;

    public ChangeReaderAction(ReaderPanel readerPanel) {
        super(readerPanel);
        this.readerPanel = readerPanel;
    }

    @Override
    public void action(ActionEvent e) throws Exception {

        PdfConfiguration configuration = readerPanel.getFormData();

        if (StringUtils.isEmpty(configuration.getFilePath())) {
            JOptionPane.showMessageDialog(readerPanel, "请选择要转换的文本文件", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (configuration.getWidth() <= 1f) {
            JOptionPane.showMessageDialog(readerPanel, "请输入PDF文件的宽度", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (configuration.getHeight() <= 1f) {
            JOptionPane.showMessageDialog(readerPanel, "请输入PDF文件的长度", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (configuration.getFontSize() <= 0) {
            JOptionPane.showMessageDialog(readerPanel, "请输入字体大小", "错误", JOptionPane.WARNING_MESSAGE);
        } else if (configuration.getPdfMaxSize() <= 0) {
            JOptionPane.showMessageDialog(readerPanel, "请输入文件大小", "错误", JOptionPane.WARNING_MESSAGE);
        } else {
            PdfReader reader = SpringContext.getBean(PdfReader.class);

            File pdfFile = reader.createPdf(configuration);
            reader.splitPdf(configuration, pdfFile);

            JOptionPane.showMessageDialog(readerPanel, "电子书转换成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
