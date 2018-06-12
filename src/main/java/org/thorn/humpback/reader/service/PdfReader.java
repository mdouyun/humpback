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

package org.thorn.humpback.reader.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thorn.humpback.reader.entity.PdfConfiguration;

import java.io.*;

/**
 * PDF操作类.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class PdfReader {

    private static final String DEFAULT_CHARSET_CODE = "UTF-8";

    public File createPdf(PdfConfiguration pdfConfiguration) throws IOException, DocumentException {

        File txtFile = new File(pdfConfiguration.getFilePath());

        if (!txtFile.exists()) {
            throw new IOException("TXT文件不存在");
        }

        if (StringUtils.isEmpty(pdfConfiguration.getCharsetCode())) {
            pdfConfiguration.setCharsetCode(DEFAULT_CHARSET_CODE);
        }

        if (StringUtils.isEmpty(pdfConfiguration.getPdfFolder())) {
            pdfConfiguration.setPdfFolder(txtFile.getParent());
        }

        if (StringUtils.isEmpty(pdfConfiguration.getPdfName())) {
            String name = txtFile.getName();
            if (name.contains(".")) {
                name = name.substring(0, name.lastIndexOf("."));
            }
            pdfConfiguration.setPdfName(name + ".pdf");
        }

        File pdfFolder = new File(pdfConfiguration.getPdfFolder());
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
        }

        File pdfFile = new File(pdfFolder, pdfConfiguration.getPdfName());
        pdfFile.createNewFile();

        InputStream input = null;
        BufferedReader reader = null;
        Document document = new Document();
        OutputStream output = null;

        Rectangle pageSize = new Rectangle(pdfConfiguration.getWidth(), pdfConfiguration.getHeight());
        document.setPageSize(pageSize);
        try {
            output = new FileOutputStream(pdfFile);

            PdfWriter.getInstance(document, output);
            document.open();

//            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
//                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            BaseFont bfChinese = BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese, pdfConfiguration.getFontSize(), Font.NORMAL);

            input = new FileInputStream(txtFile);
            InputStreamReader streamReader = new InputStreamReader(input, pdfConfiguration.getCharsetCode());
            reader = new BufferedReader(streamReader);

            String str = null;
            Paragraph rowParagraph = null;
            while ((str = reader.readLine()) != null) {
                rowParagraph = new Paragraph(str, font);
                document.add(rowParagraph);
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
            if(input != null) {
                input.close();
            }
            if(document != null) {
                document.close();
            }
            if(output != null) {
                output.close();
            }
        }

        return pdfFile;
    }

    public void splitPdf(PdfConfiguration pdfConfiguration, File pdfFile) throws IOException, DocumentException {
        long size = pdfFile.length();
        long maxSize = 1024L * 1024L * pdfConfiguration.getPdfMaxSize();

        if (maxSize >= size) {
            return;
        }

        int fileNumber = (int) (size / maxSize);
        if (size % maxSize != 0) {
            fileNumber++;
        }

        Document document = null;
        PdfCopy copy = null;

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(pdfFile.getAbsolutePath());

        int totalPage = reader.getNumberOfPages();
        int pageSize = totalPage / fileNumber;

        for (int i = 0; i < fileNumber; i++) {
            String name = pdfFile.getAbsolutePath().replaceAll(".pdf", "_" + (i + 1) + ".pdf");
            File file = new File(name);
            file.createNewFile();

            // set pageSize
            document = new Document(reader.getPageSize(1));

            copy = new PdfCopy(document, new FileOutputStream(file));
            document.open();

            for (int j = pageSize * i + 1; j <= pageSize * (i + 1) && j <= totalPage; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();
        }
    }

}
