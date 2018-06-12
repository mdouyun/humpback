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

package org.thorn.humpback.codebuilder.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.thorn.humpback.codebuilder.entity.RenderData;
import org.thorn.humpback.codebuilder.entity.TemplateFile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 模板的渲染操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class TemplateHandler {

	private Configuration cf = new Configuration();

	private File tempFolder;

	public TemplateHandler(File tempFolder) throws IOException {

        if(tempFolder == null || !tempFolder.isDirectory() || !tempFolder.exists()) {
            throw new IOException("模块目录不存在");
        }

		this.tempFolder = tempFolder;
		cf.setDirectoryForTemplateLoading(tempFolder);
		cf.setDefaultEncoding("UTF-8");
	}

	public File getTempFolder() {
		return tempFolder;
	}

	/**
	 * 递归获取目录下的所有文件
	 * 
	 * @param dir   模板目录
	 * @param list  模板文件列表
	 */
	public void listFile(File dir, List<TemplateFile> list) {

		if (dir == null) {
			return;
		}

		File[] files = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {

				if (pathname.isDirectory()) {
					return true;
				}

				String name = pathname.getName();

                return name.indexOf(".fl") > 0 && name.split("\\.").length == 3;

              }
		});

		for (File fl : files) {

			if (fl.isDirectory()) {
				listFile(fl, list);
			} else {
				list.add(new TemplateFile(fl, tempFolder));
			}
		}
	}

    /**
     * 根据模板及数据生成文件
     * @param data          数据对象
     * @param templateFile  模板文件
     * @param destination   生成的代码文件
     * @throws IOException
     * @throws TemplateException
     */
	public void applyTemplate(RenderData data, TemplateFile templateFile,
			File destination) throws IOException, TemplateException {

		String tpName = templateFile.getFolder() + File.separator
				+ templateFile.getFile().getName();
		freemarker.template.Template tp = cf.getTemplate(tpName);
		
		tp.process(data, new FileWriter(destination));
	}

}
