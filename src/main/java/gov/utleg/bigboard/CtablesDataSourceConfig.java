package gov.utleg.bigboard;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"gov.utleg.bigboard.dao.ctables"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class CtablesDataSourceConfig extends DataSourceConfig {
	
	private static Logger logger = Logger.getLogger(CtablesDataSourceConfig.class.getName());

	@Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactoryBean2() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //Can no longer use application.properties for mybatis configuration settings, so this is a hack for
        //application.properties: mybatis.configuration.map-underscore-to-camel-case=true
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        ibatisConfiguration.setVfsImpl(SpringBootVFS.class);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(ctablesDataSource());
        return sqlSessionFactoryBean.getObject();
    }
	
	@Bean(name = "ctablesDataSource")
	public DataSource ctablesDataSource() {
		
		init(); // needs to be called before any data source is configured
		
		String sDatabaseUrl = "jdbc:jtds:sqlserver://utlegsql.leg.local";

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUsername("xxxxxxxxx");
		dataSource.setPassword("xxxxxxxxx");
		dataSource.setUrl(sDatabaseUrl + "/Ctables");
		dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		
		logger.info("Connected to: " + dataSource.getUrl());

		return dataSource;
	}

}
