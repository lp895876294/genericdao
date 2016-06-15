## genericdao
为了利用`JPA`单表快速开发的优势和`Mybatis`构造SQL的灵活性，解决JPA和mybatis同时使用时的一些扩展性和兼容性问题。

##实现功能
* JPA接口和Mybatis接口使用一个接口类。使用spring-data-jpa代理接口类的实现，对于接口类中的mybatis方法，调用mybatis代理实现。
* Mybatis返回结果与实体的映射方式，与JPA实体与数据库映射的注解。
