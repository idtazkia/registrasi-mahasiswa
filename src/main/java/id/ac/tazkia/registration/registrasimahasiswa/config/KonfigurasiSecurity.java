package id.ac.tazkia.registration.registrasimahasiswa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter {
    private static final String SQL_LOGIN
            = "select u.username as username,p.password as password, active\n"
            + "FROM s_user u\n"
            + "inner join s_user_password p on p.id_user = u.id\n"
            + "WHERE u.username = ?";

    private static final String SQL_PERMISSION
            ="SELECT u.username,ur.name AS authority FROM s_user u JOIN s_role ur ON  u.id_role = ur.id\n" +
            "where u.username = ?";

    @Autowired
    private DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .and().formLogin().defaultSuccessUrl("/registrasi/form")
                .loginPage("/login")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/api/callback/bni/payment")
                .antMatchers("/info")
                .antMatchers("/js/*")
                .antMatchers("/img/*")
                .antMatchers("/css/*");
    }



}

