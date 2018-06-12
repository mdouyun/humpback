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

package org.thorn.humpback.qrcode.service;

import com.swetake.util.Qrcode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 二维码图片生成器(只支持120字节).
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class QrGenerator {

    public BufferedImage generate(String text, String imagePath) throws IOException {
        BufferedImage bi = new BufferedImage(275, 275, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 275; i++) {
            for (int j = 0; j < 275; j++) {
                bi.setRGB(j, i, Color.WHITE.getRGB());
            }
        }

        Graphics2D gs = bi.createGraphics();

        gs.setColor(Color.BLACK);

        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeEncodeMode('B');//N A ...
        qrcode.setQrcodeErrorCorrect('M');//L M Q H
        qrcode.setQrcodeVersion(7);

        boolean[][] rest = qrcode.calQrcode(text.trim().getBytes());

        for (int i = 0; i < rest.length; i++) {
            for (int j = 0; j < rest.length; j++) {
                if (rest[j][i]) {
                    gs.fillRect(j * 6, i * 6, 6, 6);
                }
            }
        }

        File qrImage = new File(imagePath);
        if (!qrImage.getParentFile().exists()) {
            qrImage.getParentFile().mkdirs();
        }

        ImageIO.write(bi, "png", qrImage);
        return bi;
    }
}
