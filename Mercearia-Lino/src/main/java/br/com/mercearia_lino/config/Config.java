package br.com.mercearia_lino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/db_mercearia_lino"); //ajustar ao conectar no computador do campus

        dataSource.setUsername("root"); //modificar os valores antes de rodar a aplicacao

        dataSource.setPassword(""); //modificar os valores antes de rodar a aplicacao

        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.MYSQL);

        adapter.setShowSql(true);

        adapter.setGenerateDdl(true);

        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        adapter.setPrepareConnection(true);

        return adapter;
    }

}
