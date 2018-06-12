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

package org.thorn.humpback.reader.view;

import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.service.WinRegistry;
import org.thorn.humpback.reader.action.ChangeReaderAction;
import org.thorn.humpback.reader.entity.PdfConfiguration;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 电子书转换主界面.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class ReaderPanel extends JPanel {

    private JTextField widthField;

    private JTextField heightField;

    private JTextField txtField;

    private JTextField maxSizeField;

    private JTextField pdfFolderField;

    private JTextField pdfNameField;

    private JComboBox charsetBox;

    private JTextField fontSizeField;

    private final static Dimension smallLabel = new Dimension(150, 25);

    private final static Dimension normalLabel = new Dimension(300, 25);

    private final static Dimension normalField = new Dimension(210, 27);

    private final static Dimension smallField = new Dimension(130, 27);

    public ReaderPanel() {
        this.setPreferredSize(new Dimension(620, 490));
        this.setBorder(BorderFactory.createEmptyBorder(40, 10, 2, 10));

        Box rowBox = Box.createHorizontalBox();

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("选择文本文件：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(normalLabel);
        rowBox.add(labelPanel);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("宽度：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(smallLabel);
        rowBox.add(labelPanel);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("长度：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(smallLabel);
        rowBox.add(labelPanel);

        this.add(rowBox);
        rowBox = Box.createHorizontalBox();

        txtField = new JTextField();
        txtField.setEditable(false);
        txtField.setPreferredSize(normalField);
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("选择文本文件");
        fileChooser.setDialogTitle("请选择电子书");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {

                if(f.isDirectory()) {
                    return true;
                }

                String name = f.getName();
                return StringUtils.endsWithIgnoreCase(name, ".txt");
            }

            @Override
            public String getDescription() {
                return "选择txt文件";
            }
        });

        final JPanel thisPanel = this;
        JButton fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(thisPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = fileChooser.getSelectedFile();
                    txtField.setText(data.getAbsolutePath());
                }
            }
        });

        rowBox.add(txtField);
        rowBox.add(Box.createHorizontalStrut(5));
        rowBox.add(fileBtn);
        rowBox.add(Box.createHorizontalStrut(20));

        widthField = new JTextField("210");
        widthField.setPreferredSize(smallField);
        rowBox.add(widthField);
        rowBox.add(Box.createHorizontalStrut(20));

        heightField = new JTextField("270");
        heightField.setPreferredSize(smallField);
        rowBox.add(heightField);
        rowBox.add(Box.createHorizontalStrut(20));

        this.add(rowBox);
        rowBox = Box.createHorizontalBox();

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("PDF目录：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(normalLabel);
        rowBox.add(labelPanel);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("PDF名称：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(smallLabel);
        rowBox.add(labelPanel);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("字体大小：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(smallLabel);
        rowBox.add(labelPanel);

        this.add(rowBox);
        rowBox = Box.createHorizontalBox();

        pdfFolderField = new JTextField();
        pdfFolderField.setEditable(false);
        pdfFolderField.setPreferredSize(normalField);
        final JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("选择目录");
        folderChooser.setDialogTitle("请选择电子书生成目录");

        WinRegistry registry = SpringContext.getBean(WinRegistry.class);
        String dir = registry.getLocation();
        folderChooser.setCurrentDirectory(new File(dir));

        fileBtn = new JButton("选择");
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = folderChooser.showOpenDialog(thisPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File data = folderChooser.getSelectedFile();
                    pdfFolderField.setText(data.getAbsolutePath());
                }
            }
        });

        rowBox.add(pdfFolderField);
        rowBox.add(Box.createHorizontalStrut(5));
        rowBox.add(fileBtn);
        rowBox.add(Box.createHorizontalStrut(20));

        pdfNameField = new JTextField();
        pdfNameField.setPreferredSize(smallField);
        rowBox.add(pdfNameField);
        rowBox.add(Box.createHorizontalStrut(20));

        fontSizeField = new JTextField("7");
        fontSizeField.setPreferredSize(smallField);
        rowBox.add(fontSizeField);
        rowBox.add(Box.createHorizontalStrut(20));

        this.add(rowBox);
        rowBox = Box.createHorizontalBox();

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("字体编码：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(normalLabel);
        rowBox.add(labelPanel);

        labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("文件大小：");
        labelPanel.add(label);
        labelPanel.setPreferredSize(smallLabel);
        rowBox.add(labelPanel);

        rowBox.add(Box.createHorizontalStrut(150));
        this.add(rowBox);
        rowBox = Box.createHorizontalBox();

        charsetBox = new JComboBox(new String[]{"UTF-8", "GBK"});
        charsetBox.setPreferredSize(new Dimension(275, 27));
        rowBox.add(charsetBox);
        rowBox.add(Box.createHorizontalStrut(20));

        maxSizeField = new JTextField("5");
        maxSizeField.setPreferredSize(smallField);
        rowBox.add(maxSizeField);

        label = new JLabel("MB");
        rowBox.add(label);

        rowBox.add(Box.createHorizontalStrut(150));
        this.add(rowBox);

        this.add(Box.createVerticalStrut(100));
        JButton button = new JButton("转换电子书");
        button.addActionListener(new ChangeReaderAction(this));
        button.requestFocus();
        rowBox = Box.createHorizontalBox();
        rowBox.add(button);
        this.add(rowBox);
    }

    public PdfConfiguration getFormData() {
        PdfConfiguration configuration = new PdfConfiguration();

        configuration.setFilePath(txtField.getText());
        configuration.setPdfName(pdfNameField.getText());
        configuration.setPdfFolder(pdfFolderField.getText());

        String maxSize = maxSizeField.getText();
        if(StringUtils.isNotBlank(maxSize)) {
            configuration.setPdfMaxSize(Integer.parseInt(maxSize));
        }

        String width = widthField.getText();
        String height = heightField.getText();
        String fontSize = fontSizeField.getText();
        if(StringUtils.isNotBlank(width)) {
            configuration.setWidth(Float.parseFloat(width));
        }
        if(StringUtils.isNotBlank(height)) {
            configuration.setHeight(Float.parseFloat(height));
        }
        if(StringUtils.isNotBlank(fontSize)) {
            configuration.setFontSize(Integer.parseInt(fontSize));
        }

        configuration.setCharsetCode((String) charsetBox.getSelectedItem());


        return configuration;
    }

}
