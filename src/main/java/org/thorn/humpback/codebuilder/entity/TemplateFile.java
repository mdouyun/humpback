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

package org.thorn.humpback.codebuilder.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 代码模板相关配置信息.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class TemplateFile {

    /** 模板文件. */
	private File file;
	/** 模板类型后缀. */
	private String type;
	
	private String folder;
	
	private String name;
	
	public TemplateFile(File file, File root) {
		this.file = file;
		
		String fileName = file.getName();
		String[] array = fileName.split("\\.");
		
		this.type = array[0];
		this.name = array[1];
		this.folder = StringUtils.remove(file.getParent(), root.getPath());
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
