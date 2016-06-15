package com.dao.genericdao.dialect;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * 不生成外键，通过类似于SQL注入的方法，为每个数据库修改创建外键的SQL
 */
public class Oracle10gDialectWithoutFK extends Oracle10gDialect {
	@Override
	public String getAddForeignKeyConstraintString(
			String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {
//		通过修改外键列的默认值，而不是添加外键，避免生成外键
		return " modify "+ foreignKey[0] +" default null " ;
	}
}
