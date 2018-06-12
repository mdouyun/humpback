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

/**
 * 数据库字段与java对象的1:1映射.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class Field implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5382511124963448144L;
	
	/**
	 * 是否主键（true是\false否）
	 */
	private boolean key;
	
	/**
	 * 数据库字段名称（大写）
	 */
	private String tabName;
	
	/**
	 * 数据库字段类型
	 */	
	private String tabType;
	
	/**
	 * 字段长度
	 */		
	private int tabLength;
	
	/**
	 * 数据库字段类型对应的java的jdbc类型
	 */		
	private int javaType;
	
	/**
	 * 数据库字段描述
	 */		
	private String comment;
	
	/**
	 * 实体类名称
	 */		
	private String fieldName;

	/**
	 * 实体类型
	 */
	private String fieldType;

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public int getTabLength() {
		return tabLength;
	}

	public void setTabLength(int tabLength) {
		this.tabLength = tabLength;
	}

    public int getJavaType() {
        return javaType;
    }

    public void setJavaType(int javaType) {
        this.javaType = javaType;
    }

    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
}
