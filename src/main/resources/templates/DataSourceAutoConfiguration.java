package com.gezhiwei.local.fileclean.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Magoo
 */
@Configuration
@MapperScan(basePackages = "{{packageName}}.dao",
        sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceAutoConfiguration {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.store")
    @Primary
    public DataSource backstageDataSource() {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create().build();
        dataSource.setMaximumPoolSize(400);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryOfBackstage(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources1 = pathMatchingResourcePatternResolver.getResources("classpath:mybatis/mybatis/*.xml");
        Resource[] resources2 = pathMatchingResourcePatternResolver.getResources("classpath:mybatis/cmapper/*.xml");
        Resource[] resources = new Resource[resources1.length + resources2.length];
        System.arraycopy(resources1, 0, resources, 0, resources1.length);
        System.arraycopy(resources2, 0, resources, resources1.length, resources2.length);
        bean.setMapperLocations(resources);
        return bean.getObject();

    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager backstageTransactionManager(
            @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}