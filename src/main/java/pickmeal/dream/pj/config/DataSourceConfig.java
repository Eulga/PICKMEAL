package pickmeal.dream.pj.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 
 * @author 김보령
 *
 */
@Configuration
@ComponentScan(basePackages = "pickmeal.dream.pj")
@EnableTransactionManagement
public class DataSourceConfig {
	@Bean
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://pickmeal.cqtn0yp7u2ml.ap-northeast-2.rds.amazonaws.com:3306/pickmeal");
		ds.setUsername("root");
		ds.setPassword("adminadmin");
		
		ds.setInitialSize(10);
		ds.setMaxActive(50);
		ds.setMaxIdle(50);
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}
}
