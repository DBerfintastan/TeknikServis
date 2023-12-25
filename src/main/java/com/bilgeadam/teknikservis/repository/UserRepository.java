package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;


    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder= passwordEncoder;
    }


    public boolean save (User user){

        String sql = "insert into \"public\".\"USERS\" (\"username\",\"email\",\"password\",\"role\") values (:username, :email, :password, :role)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username" , user.getUsername());
        paramMap.put("email" , user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        paramMap.put("password" , user.getPassword());
        user.setRole("ROLE_USER");
        paramMap.put("role" , user.getRole());
        return  namedParameterJdbcTemplate.update(sql , paramMap) == 1;


    }

    public List<User> getall(){
        return jdbcTemplate.query("select * from \"public\".\"USERS\" order by \"id\" asc", BeanPropertyRowMapper.newInstance(User.class));
    }
}
