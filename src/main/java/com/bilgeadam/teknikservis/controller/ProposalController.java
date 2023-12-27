package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Proposal;
import com.bilgeadam.teknikservis.model.ProposalDTO;
import com.bilgeadam.teknikservis.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposal/user")
public class ProposalController {
    private final ProposalRepository proposalRepository;

    @Autowired
    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Proposal>> getAll() {
        return ResponseEntity.ok(proposalRepository.getAllForUser());
    }
    /*
    @GetMapping("/getAllProposalDTO")
    public ResponseEntity<List<ProposalDTO>> getAllProposalDTO() {

        return ResponseEntity.ok(proposalRepository.getAllForUserDTO());
    }
     */

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean result = proposalRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Proposal Deleted Successfully ID->" + id);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not remove this proposal");
    }

    @PostMapping("/saveWithDTO")
    public ResponseEntity<String> save(@RequestBody ProposalDTO proposalDTO) {
        boolean result = proposalRepository.saveWithDTO(proposalDTO);
        if (result)
            return ResponseEntity.ok("Proposal  Saved with Dto  Successfully");
        else
            return ResponseEntity.badRequest().build();
    }
    @PostMapping("/saveWithId")
    public ResponseEntity<String> save(@RequestBody Proposal proposal) {
        boolean result = proposalRepository.saveWithId(proposal);
        if (result)
            return ResponseEntity.ok("Proposal Saved with Id Successfully");
        else
            return ResponseEntity.badRequest().build();
    }
}
