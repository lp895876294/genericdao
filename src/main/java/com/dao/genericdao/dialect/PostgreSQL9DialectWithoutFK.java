package com.dao.genericdao.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;

/**
 * 不生成外键，通过类似于SQL注入的方法，为每个数据库修改创建外键的SQL
 */
public class PostgreSQL9DialectWithoutFK extends PostgreSQL9Dialect {
	@Override
	public String getAddForeignKeyConstraintString(
			String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {
//		设置foreignkey对应的列值可以为空
		return " alter "+ foreignKey[0] +" set default null " ;
	}
}
