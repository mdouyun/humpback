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

package org.thorn.humpback.localpass.service;

import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 文件读与写操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class FileStore {

    private static String CHARSET_CODE = "UTF-8";

    public void write(String fileName, String content) throws IOException {
        File file = new File(fileName);
        File folder = new File(file.getParent());

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        OutputStream output = null;
        try {
            byte[] bytes = content.getBytes(CHARSET_CODE);

            output = new FileOutputStream(file);
            output.write(bytes);
            output.flush();

        } finally {
            if(output != null) {
                output.close();
            }
        }
    }

    public String read(String fileName) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            throw new IOException(fileName + " not exists!");
        }

        StringBuilder content = new StringBuilder();
        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = new FileInputStream(file);
            InputStreamReader streamReader = new InputStreamReader(input, CHARSET_CODE);
            reader = new BufferedReader(streamReader);
            content.append(reader.readLine());

            String str = null;
            while ((str = reader.readLine()) != null) {
                content.append("\n");
                content.append(str);
            }
        } finally {
            if(input != null) {
                input.close();
            }
            if(reader != null) {
                reader.close();
            }
        }

        return content.toString();
    }
}
