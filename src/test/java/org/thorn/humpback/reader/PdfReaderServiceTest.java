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

package org.thorn.humpback.reader;

import junit.framework.TestCase;
import org.thorn.humpback.reader.entity.PdfConfiguration;
import org.thorn.humpback.reader.service.PdfReader;

import java.io.File;

/**
 *
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class PdfReaderServiceTest extends TestCase {

    PdfReader pdfReaderService = new PdfReader();

    PdfConfiguration pdfConfiguration = new PdfConfiguration();

    public void testCreatePdf() throws Exception {

        pdfConfiguration.setWidth(600);
        pdfConfiguration.setHeight(800);
        pdfConfiguration.setFontSize(8);

        pdfConfiguration.setFilePath("D:\\API\\JDOM_API.txt");
        pdfConfiguration.setCharsetCode("gbk");


        pdfReaderService.createPdf(pdfConfiguration);
    }

    public void testSplitPdf() throws Exception {
        pdfConfiguration.setPdfMaxSize(1);

        pdfConfiguration.setPdfFolder("D:\\API\\");
        pdfConfiguration.setPdfName("txj.pdf");

        pdfReaderService.splitPdf(pdfConfiguration, new File("D:\\API\\txj.pdf"));
    }
}
