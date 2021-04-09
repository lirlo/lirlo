package com.lirlo.baseplat.system.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.ArrayList;
import java.util.List;

public class SqlParser {

    static List<String> parser(){

        List<String> tables = new ArrayList<>();

        String sql = "insert into dwm.b select user from awd.emp_table t left join  ccs.user a on t.id = a.uid ";
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);
            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            stmt.accept(visitor);

            if(visitor.getTables() != null){
                System.out.println("Manipulation : " + visitor.getTables());
                visitor.getTables().forEach((k,v)->{
                    tables.add(k.getName());
                    System.out.println(k.getName());
                });
            }
        }

        return tables;
    }

    public static void main(String[] args) {
        parser();
    }
}
