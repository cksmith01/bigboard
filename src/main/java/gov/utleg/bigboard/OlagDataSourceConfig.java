package gov.utleg.bigboard;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"gov.utleg.springboot3.dao.olag"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class OlagDataSourceConfig extends DataSourceConfig {
	
	private static Logger logger = Logger.getLogger(OlagDataSourceConfig.class.getName());

	/*
	 * references...
	 * 		https://github.com/kazuki43zoo/mybatis-spring-boot-multi-ds-demo/blob/master/src/main/java/com/example/MybatisDemoApplication.java
	 * 		https://github.com/mybatis/spring-boot-starter/issues/78
	 */
	
	@Bean(name = "sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //Can no longer use application.properties for mybatis configuration settings, so this is a hack for
        //application.properties: mybatis.configuration.map-underscore-to-camel-case=true
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        ibatisConfiguration.setVfsImpl(SpringBootVFS.class);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(olagDataSource());
        return sqlSessionFactoryBean.getObject();
    }
	
	@Bean(name = "olagDataSource")
	@Primary
	public DataSource olagDataSource() {
		
		init(); // needs to be called before any data source is configured

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUsername("xxxxxxxxxx");
		dataSource.setPassword("xxxxxxxxxx");
		dataSource.setUrl(sDatabaseUrl + "/OLAGDB");
		dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		
		logger.info("Connected to: " + dataSource.getUrl());

		return dataSource;
	}

}
