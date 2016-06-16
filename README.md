## genericdao - 结合了JPA和Mybatis各自优势的DAO插件
利用`JPA`单表快速开发的优势和`Mybatis`构造SQL的灵活性，解决JPA和mybatis同时使用时的一些扩展性和兼容性问题。

###功能简介
* JPA接口和Mybatis接口使用同一个DAO接口，其中Mybatis的接口方法使用注解单独标注。
* DAO接口代理使用spring-data-jpa的实现机制，对spring-date-jpa中DAO接口方法的查找机制进行了扩展。对于接口中的mybatis方法，调用mybatis的Mapper代理实现。
* 扩展Mybatis查询结果和实体属性的映射方式。查询结果首先与带有JPA @Column注解的类属性进行匹配，然后再使用Mybatis自带映射方式。
* 实现的功能不对spring-data-jpa和Mybatis的源码做任何修改，只对它们的实现机制进行扩展，避免重复造轮子。

###Quick start
**JPA配置**</br>
``` java
@EnableJpaRepositories(basePackages={"basePackages"} ,
	repositoryFactoryBeanClass=GenericJpaRepositoryFactoryBean.class) 
```
使用javaConfig配置方法，其中GenericJpaRepositoryFactoryBean是为扩展后的JpaRepositoryFactory，其中定义了扩展后的JPA底层DAO接口及接口方法的查找方式。

**Mybatis配置**</br>
``` java
//创建Configuration对象
Configuration configuration = new Configuration() ;
//替换Configuration中默认的ObjectWrapperFactory
configuration.setObjectWrapperFactory(new JPAObjectWrapperFactory()) ;
//替换sqlSessionFactoryBean中默认的Configuration对象
sqlSessionFactoryBean.setConfiguration(configuration);
```
在ObjectWrapperFactory能够指定ObjectWrapper，ObjectWrapper是Mybatis提供的一种java对象反射类，通过ObjectWrapper能够获取和设置object对象的属性。

**参考开源项目** </br>
[spring-data-jpa-extra](https://github.com/slyak/spring-data-jpa-extra)    </br>
[MyBatis通用Mapper3](https://github.com/abel533/Mapper)


