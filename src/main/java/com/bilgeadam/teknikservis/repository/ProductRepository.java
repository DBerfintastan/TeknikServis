package com.bilgeadam.teknikservis.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
@Repository
public class ProductRepository {
	private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ProductRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.userRepository = userRepository;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	 public List<Product> getAll()
	    {
	        String sql = "SELECT * FROM public.\"PRODUCT\" order by \"id\" asc";
	        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
	    }
}
