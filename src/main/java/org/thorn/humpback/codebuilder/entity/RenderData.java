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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 渲染代码模板的数据模型.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class RenderData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6074929541203563326L;
	
	/**
	 * 模块名称
	 */
	private String name;
	
	/**
	 * 数据库表名（大写）
	 */
	private String tableName;
	
	/**
	 * 包根路径（小写）
	 */
	private String pkg;
	
	/**
	 * 属性信息（数据库字段与实体类信息）
	 */
	private List<Field> fields = new ArrayList<Field>();

    public RenderData(String name, String tableName, String pkg, List<Field> fields) {
        this.name = name;
        this.tableName = tableName;
        this.pkg = pkg;
        this.fields = fields;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return 全大写的模块名称
	 */
	public String getNameAllUpperCase() {
		return name.toUpperCase();
	}
	
	/**
	 * 
	 * @return 全小写的模块名称
	 */
	public String getNameAllLowerCase() {
		return name.toLowerCase();
	}
	
	/**
	 * 
	 * @return 第一个字母小写的模块名称
	 */
	public String getNameFirLetterLc() {
		String firLetter = name.substring(0, 1);
		return name.replaceFirst(firLetter, firLetter.toLowerCase());
	}
	
	/**
	 * 
	 * @return 第一个字母大写的模块名称
	 */
	public String getNameFirLetterUc() {
		String firLetter = name.substring(0, 1);
		return name.replaceFirst(firLetter, firLetter.toUpperCase());
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
