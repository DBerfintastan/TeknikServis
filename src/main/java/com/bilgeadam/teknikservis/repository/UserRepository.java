package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.Role;
import com.bilgeadam.teknikservis.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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


    public List<GrantedAuthority> getUserRoles(String username)
    {
        String sql = "SELECT \"authority\" FROM \"public\".\"authorities\" where \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);
        List<String> liste = namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);


        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : liste)
        {
            roles.add(new Role(role));
        }
        return roles;
    }

    public User getByUsername(String username){
        String sql="select * from \"public\".\"users\" where \"username\" = :Username";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("Username", username);
        return namedParameterJdbcTemplate.queryForObject(sql,paramMap,BeanPropertyRowMapper.newInstance(Users.class));

    }

    public boolean save (User user, Role role){

        String sql = "insert into \"public\".\"USERS\" (\"username\",\"email\",\"password\",\"role\") values (:username, :email, :password, :role)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username" , user.getUsername());
        paramMap.put("email" , user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        paramMap.put("password" , user.getPassword());
        paramMap.put("role" , user.getRole());
        return  namedParameterJdbcTemplate.update(sql , paramMap) == 1;


    }

    public List<User> getall(){
        return jdbcTemplate.query("select * from \"public\".\"USERS\" order by \"id\" asc", BeanPropertyRowMapper.newInstance(User.class));
    }
}
