package com.kh.userservice.application;




public class Consts {

public static  String database_url ="jdbc:h2:mem:testDb";
public static  String database_username="sa";
public static  String database_driver="org.h2.Driver";
public static  String database_dialect="org.hibernate.dialect.H2Dialect";

public static  enum authority  {ROLE_ADMIN, ROLE_GUEST, CREATE, READ, DELETE, PRINT};

}
