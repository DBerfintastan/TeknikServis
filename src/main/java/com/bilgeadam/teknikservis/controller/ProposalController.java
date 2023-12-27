package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Proposal;
import com.bilgeadam.teknikservis.model.ProposalAdminDto;
import com.bilgeadam.teknikservis.model.ProposalDTO;
import com.bilgeadam.teknikservis.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    private final ProposalRepository proposalRepository;

    @Autowired
    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<List<Proposal>> getAll() {
        return ResponseEntity.ok(proposalRepository.getAllForUser());
    }
    /*
    @GetMapping("/getAllProposalDTO")
    public ResponseEntity<List<ProposalDTO>> getAllProposalDTO() {

        return ResponseEntity.ok(proposalRepository.getAllForUserDTO());
    }
     */

    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean result = proposalRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Proposal Deleted Successfully ID->" + id);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not remove this proposal");
    }

    @PostMapping("/user/saveWithDTO")
    public ResponseEntity<String> save(@RequestBody ProposalDTO proposalDTO) {
        boolean result = proposalRepository.saveWithDTO(proposalDTO);
        if (result)
            return ResponseEntity.ok("Proposal  Saved with Dto  Successfully");
        else
            return ResponseEntity.badRequest().build();
    }
    @PostMapping("/user/saveWithId")
    public ResponseEntity<String> save(@RequestBody Proposal proposal) {
        boolean result = proposalRepository.saveWithId(proposal);
        if (result)
            return ResponseEntity.ok("Proposal Saved with Id Successfully");
        else
            return ResponseEntity.badRequest().build();
    }
    @GetMapping(path = "admin/getalldto",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProposalAdminDto>> getalldto()
    {
        return ResponseEntity.ok(proposalRepository.getAllDTO());
    }
    @PutMapping(path = "/admin/updatetruestatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable(name = "id") long id)
    {
        try
        {
            boolean result = proposalRepository.updateTrueStatus(id);
            String messages ="Updated your uptaded data is -->"+proposalRepository.getById(id);
            if (result)
            {
                return ResponseEntity.ok(messages);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Not Updated");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal 500 error.");
        }
    }
    @PutMapping(path = "/admin/updatefalsestatus/{id}")
    public ResponseEntity<String> updateFalseStatus(@PathVariable(name = "id") long id)
    {
        try
        {
            boolean result = proposalRepository.updateFalseStatus(id);
            String messages ="Updated your uptaded data is -->"+proposalRepository.getById(id);
            if (result)
            {
                return ResponseEntity.ok(messages);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Not Updated");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal 500 error.");
        }
    }
    @GetMapping(path = "/admin/getbyiddto/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProposalAdminDto> getbyiddto(@PathVariable(name = "id") long id)
    {
        try{
            return ResponseEntity.ok(proposalRepository.getAllDTOById(id));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }
}
