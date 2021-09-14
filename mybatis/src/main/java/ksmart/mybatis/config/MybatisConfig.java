package ksmart.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(value="ksmart.mybatis.mapper", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class MybatisConfig {
	
	/**
	 * Mybatis 연동을 위한 SqlSessionFactory 설정
	 * 데이터베이스 연결정보와 mybatis 설정정보 => 데이터베이스 연결과 SQL의 실행에 모든 것을 관리하는 객체 (Sqlsession)
	 * @param dataSource db연결정보
	 * @param ApplicationContext 스프링 코어 접근 (프로젝트 전반 설정) 
	 * @return SqlSessionFactory 빈 생성 
	 * @throws Exception
	 */
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory mybatisSqlSessionFactory(DataSource dataSource, ApplicationContext ctx) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(ctx.getResources("classpath:mapper/**/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("ksmart.mybatis.domain");
		return sqlSessionFactoryBean.getObject();
	}
	
	/**
	 * Spring Mybatis SqlSessionTemplate 설정
	 * SqlSessionTemplate은 Mybatis 스프링 연동모듈의 핵심. Sqlsession을 스프링이 대신 관리, 트랜잭션관리, 쓰레드에 안전.
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean(name="mybatisSqlSessionTemplate")
	public SqlSessionTemplate mybatisSqlSessionTemplate(
			@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/**
	 * TransactionManager 트랜잭션 관리
	 * @param dataSource
	 * @return
	 */
	@Bean(name="mybatisTransactionManager")
	public PlatformTransactionManager mybatisTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
