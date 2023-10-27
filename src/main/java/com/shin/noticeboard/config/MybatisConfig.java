package com.shin.noticeboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;


import org.springframework.context.annotation.ImportResource;
import javax.sql.DataSource;

@Configuration
@ImportResource("classpath:/spring/application-datasource.xml")
public class MybatisConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;
}

    // @Value("${spring.datasource.url}")
    // private String dataSourceUrl;

    // @Value("${spring.datasource.username}")
    // private String dataSourceUsername;

    // @Value("${spring.datasource.password}")
    // private String dataSourcePassword;

    // @Value("${spring.datasource.driverClassName}")
    // private String dataSourceDriverClassName;

    // @Value("${mybatis.mapper-locations}")
    // private String mapperLocations;

    // @Bean
    // public DataSource dataSource() {
    //     DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //     dataSource.setUrl(dataSourceUrl);
    //     dataSource.setUsername(dataSourceUsername);
    //     dataSource.setPassword(dataSourcePassword);
    //     dataSource.setDriverClassName(dataSourceDriverClassName);
    //     return dataSource;
    // }

    // @Bean
    // public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
    //     SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //     sessionFactory.setDataSource(dataSource());
    //     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
    //     return sessionFactory;
    // }

