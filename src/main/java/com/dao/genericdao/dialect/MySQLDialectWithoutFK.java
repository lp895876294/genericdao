package com.dao.genericdao.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.internal.util.StringHelper;

public class MySQLDialectWithoutFK extends MySQL5InnoDBDialect{
	@Override
	public String getAddForeignKeyConstraintString(
			String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {
		final String cols = StringHelper.join( ", ", foreignKey );
		//		设置foreignkey对应的列值可以为空
		return " ALTER "+ cols +" SET DEFAULT NULL " ;
	}
}
