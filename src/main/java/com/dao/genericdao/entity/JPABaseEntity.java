package com.dao.genericdao.entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 统一定义id的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * @MappedSuperclass 声明为父类，不生成实体表
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class JPABaseEntity extends JPAIdEntity {

	private static final long serialVersionUID = -779979211709378717L;
    
	@Column(name="create_user_id", length=32,updatable=false,nullable=true)
	private String createUserId ;
  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false,nullable=true)
	private Date createDatetime ;

	@Column(name="last_update_user_id", length=32,nullable=true)
	private String lastUpdateUserId ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update_time" , nullable=true)
	private Date lastUpdateDatetime ;

}
