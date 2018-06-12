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

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.thorn.humpback.codebuilder.entity.DBConfig;
import org.thorn.humpback.codebuilder.entity.Field;
import org.thorn.humpback.codebuilder.entity.JDBCTypesMapping;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class DBOperator {

    private JdbcTemplate jdbcTemplate;

    public DBOperator(DBConfig dbConfig) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
        dataSource.setDriverClassName(dbConfig.getDriverClass());

        Connection connection = dataSource.getConnection();
        connection.close();

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean isTableExist(String table) {

        String sql = "select count(*) as num from " + table;

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void dropTable(String table) throws SQLException {

        String sql = "drop table " + table;

        executeSql(sql);
    }

    public void executeSql(String sql) throws SQLException {
        String[] batchSql = sql.split(";");

        for (String one : batchSql) {
            if (StringUtils.isNotBlank(one)) {
                jdbcTemplate.execute(one);
            }

        }
    }

    public List<Field> queryDBField(String table) throws SQLException {

        List<Field> fieldList = new ArrayList<Field>();

        Connection conn = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();

            ResultSet rs = dbMetaData.getColumns("", "", table, "");

            while (rs.next()) {
                // 来自 java.sql.Types 的 SQL 类型
                int dataType = rs.getInt("DATA_TYPE");
                // 列大小
                int columnSize = rs.getInt("COLUMN_SIZE");
                // 列的名称
                String columnName = rs.getString("COLUMN_NAME");
                // 描述列的注释（可为 null）
                String remarks = rs.getString("REMARKS");
                // 默认值
                String defVal = rs.getString("COLUMN_DEF");

                Field field = new Field();
                field.setTabName(columnName.toUpperCase());
                field.setTabLength(columnSize);
                field.setJavaType(dataType);
                field.setFieldName(columnName);
                field.setFieldType(JDBCTypesMapping.getJavaTypeName(dataType));
                field.setTabType(JDBCTypesMapping.getJDBCTypeName(dataType));
                field.setComment(remarks);

                fieldList.add(field);
            }

            rs.close();

            rs = dbMetaData.getPrimaryKeys("", "", table);

            while (rs.next()) {
                // 列的名称
                String columnName = rs.getString("COLUMN_NAME");
                for(Field field : fieldList) {
                    if(StringUtils.equalsIgnoreCase(columnName, field.getTabName())) {
                        field.setKey(true);
                    }
                }
            }

            rs.close();
        } finally {
            conn.close();
        }

        return fieldList;
    }

}
