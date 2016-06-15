## genericdao
为了利用`JPA`单表快速开发的优势和`Mybatis`构造SQL的灵活性，解决JPA和mybatis同时使用时的一些扩展性和兼容性问题。

##实现功能
* JPA接口和Mybatis接口使用一个接口类。使用spring-data-jpa代理接口类的实现，对于接口类中的mybatis方法，调用mybatis代理实现。
* 修改Mybatis查询结果和实体的映射方式，映射方式和JPA查询结果的映射方式相同。

##Quick start
###JPA配置

###Mybatis配置

###参考开源项目
[spring-data-jpa-extra](https://github.com/slyak/spring-data-jpa-extra)    </br>
[MyBatis通用Mapper3](https://github.com/abel533/Mapper)


