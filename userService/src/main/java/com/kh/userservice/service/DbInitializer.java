package com.kh.userservice.service;

import com.kh.userservice.application.Consts;
import com.kh.userservice.model.AuthorityModel;
import com.kh.userservice.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


public class DbInitializer {



    public static void createOrUpdateTables(){


        Configuration config = new Configuration();//.configure();
        config.addPackage("com.kh.userservice.model");

// 1. اتصال به پایگاه داده (الزامی)
        config.setProperty("hibernate.connection.driver_class", Consts.database_driver);
        config.setProperty("hibernate.connection.url", Consts.database_url);
        config.setProperty("hibernate.connection.username", Consts.database_username);
       // config.setProperty("hibernate.connection.password", "your_password");

// 2. دیالکت (توصیه می‌شود به صراحت تنظیم شود، Hibernate 7 معمولاً می‌تواند به طور خودکار تشخیص دهد، اما برای اطمینان از سازگاری بهتر است نوشته شود)
        config.setProperty("hibernate.dialect", Consts.database_dialect);

// 3. رفتار SQL و schema (توصیه می‌شود در محیط توسعه فعال شود)
        config.setProperty("hibernate.show_sql", "true");        // چاپ SQL
        config.setProperty("hibernate.format_sql", "true");      // قالب‌بندی SQL
        config.setProperty("hibernate.hbm2ddl.auto", "update");  // به‌روزرسانی خودکار ساختار جدول (در محیط تولید با احتیاط استفاده شود)

// 4. ساخت SessionFactory
       // SessionFactory sessionFactory =
        config.buildSessionFactory();
    }


}
