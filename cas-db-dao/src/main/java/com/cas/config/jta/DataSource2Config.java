package com.cas.config.jta;

import com.cas.config.db2DateSource.DB2Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.cas.db2.mapper", sqlSessionTemplateRef = "db2SqlSessionTemplate")
public class DataSource2Config {

    private DB2Properties db2Properties;

    @Autowired
    private Environment env;

    @Autowired
    public void setDb2Properties(DB2Properties db2Properties) {
        this.db2Properties = db2Properties;
    }

    @PostConstruct
    public void getDataSource() {
        log.info("配置信息" + env.getProperty("spring.profiles.active"));
    }


    @Bean
    public DataSource db2DataSource() throws SQLException {
        // 首先要注意的是MySQL使用的DataSource 是MysqlXADataSource
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(db2Properties.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(db2Properties.getPassword());
        mysqlXaDataSource.setUser(db2Properties.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);


        //再者就是要把是MysqlXADataSource设置到AtomikosDataSourceBean中
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("db2DataSource");
        xaDataSource.setMinPoolSize(db2Properties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(db2Properties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(db2Properties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(db2Properties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(db2Properties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(db2Properties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(db2Properties.getMaxIdleTime());
        xaDataSource.setTestQuery(db2Properties.getTestQuery());

        return xaDataSource;
    }

    @Bean
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/cas/db2/mapper/*.xml"));
        return bean.getObject();
    }

//    @Bean
//    public DataSourceTransactionManager db2TransactionManager(@Qualifier("db2DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
