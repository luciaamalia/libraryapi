package io.github.cursospringjpa.libraryapi.config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration //classe de configuracao para o banco de dados
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //    @Bean
    //cria e configura um data source usando o DriverManagerDataSource
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }
    //dois metodos para fornecer data source
    /**
     * configuracao Hikary
     * https://github.com/brettwooldridge/HikariCP
     *
     * @return
     */
    @Bean
    public DataSource hikariDataSource() { //configuracao usando o Hikari

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); // maximo de conexões liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms (10 minutos)
        config.setConnectionTimeout(100000); // timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de teste

        return new HikariDataSource(config);
    }

    /**
     *
     *A classe DatabaseConfiguration é responsável por configurar o acesso ao banco de dados na aplicação.
     *
     * Ela usa o HikariCP para criar um pool de conexões JDBC, que é
     * mais eficiente do que uma implementação simples como DriverManagerDataSource.
     *
     * As configurações do pool (como tamanho máximo, tempo de vida das conexões,
     * etc.) são definidas para otimizar o uso de recursos e garantir que a aplicação funcione
     * de forma estável e performática.
     */
}