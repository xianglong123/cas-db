package com.cas.config.jta;

import com.cas.config.db1DateSource.DB1Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author: xianglong[xiang_long@suixingpay.com]
 * @date: 10:58 2020-04-04
 * @version: V1.0
 * @review:
 */
@Configuration
@MapperScan(basePackages = "com.cas.db1.mapper", sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class DataSource1Config {

    private DB1Properties db1Properties;

    @Autowired
    public void setDb1Properties(DB1Properties db1Properties) {
        this.db1Properties = db1Properties;
    }

    @Bean
    @Primary
    public DataSource db1DataSource() throws SQLException {
        // 首先要注意的是MySQL使用的DataSource 是MysqlXADataSource
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(db1Properties.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(db1Properties.getPassword());
        mysqlXaDataSource.setUser(db1Properties.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);


        //再者就是要把是MysqlXADataSource设置到AtomikosDataSourceBean中
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("db1DataSource");
        xaDataSource.setMinPoolSize(db1Properties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(db1Properties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(db1Properties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(db1Properties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(db1Properties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(db1Properties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(db1Properties.getMaxIdleTime());
        xaDataSource.setTestQuery(db1Properties.getTestQuery());

        return xaDataSource;
    }


    @Bean
    @Primary
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/cas/db1/mapper/*.xml"));
        return bean.getObject();
    }

//    @Bean
//    @Primary
//    public DataSourceTransactionManager db1TransactionManager(@Qualifier("db1DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
