# genericdao
融合JPA和Mybatis两个DAO框架，利用现有框架接口将二者结合后在一个DAO接口中使用，避免分别创建DAO接口，在一个DAO接口中，通过注解区分JPA方法和mybatis方法。

主要为了利用JPA在单表快速开发的优势和Mybatis构造SQL的灵活性。

1.JPA中实体和数据库的映射。
2.JPA单表维护操作。
3.Mybatis的SQL构造查询。
4.mybatis查询结果的字段映射方式使用JPA的映射。

