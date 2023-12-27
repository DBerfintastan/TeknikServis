package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.Proposal;
import com.bilgeadam.teknikservis.model.ProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProposalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProposalRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository, ProductRepository productRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Proposal> getAllForUser() {
        String sql = "Select * From \"PROPOSAL\" WHERE \"user_id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", userRepository.getCurrentUserId());
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    //THIS METHOD FOR PROPOSAL_DTO
    /*
    public List<ProposalDTO> getAllForUserDTO() {

        List<ProposalDTO> proposalDTOS = new ArrayList<>();
        List<Proposal> proposals = getAllForUser();

        for (Proposal proposal : proposals) {
            ProposalDTO proposalDTO = new ProposalDTO();

            proposalDTO.setNote(proposal.getNote());
            proposalDTO.setPrice(proposal.getPrice());
            String productName = productRepository.getProductNameById(proposal.getProduct_id());
            proposalDTO.setProductName(productName);

            proposalDTOS.add(proposalDTO);
        }

        return proposalDTOS;
    }

     */

    public Proposal getById(long id) {
        String sql = "Select * From \"PROPOSAL\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    public boolean deleteById(long id) {
        String sql = "Delete From \"PROPOSAL\" Where \"id\" = :ID And \"user_id\" = :USER_ID";
        Map<String, Long> params = new HashMap<>();
        params.put("ID", id);
        params.put("USER_ID", userRepository.getCurrentUserId());

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean saveWithDTO(ProposalDTO proposalDTO) {
        String sql = "Insert Into \"PROPOSAL\"(\"note\", \"price\", \"user_id\", \"status\", \"product_id\") Values(:NOTE, :PRICE, :USER_ID, :STATUS, :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", proposalDTO.getNote());
        params.put("PRICE", proposalDTO.getPrice());
        params.put("USER_ID", userRepository.getCurrentUserId());
        params.put("STATUS", false);
        params.put("PRODUCT_ID", productRepository.getProductIdByName(proposalDTO.getProductName().toUpperCase()));

        return jdbcTemplate.update(sql, params) == 1;
    }
    public boolean saveWithId(Proposal proposal) {
        String sql = "Insert Into \"PROPOSAL\"(\"note\", \"price\", \"user_id\", \"status\", \"product_id\") Values(:NOTE, :PRICE, :USER_ID, :STATUS, :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", proposal.getNote());
        params.put("PRICE", proposal.getPrice());
        params.put("USER_ID", userRepository.getCurrentUserId());
        params.put("STATUS", false);
        params.put("PRODUCT_ID", proposal.getProduct_id());

        return jdbcTemplate.update(sql, params) == 1;
    }
}
