package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Proposal;
import com.bilgeadam.teknikservis.model.ProposalAdminDto;
import com.bilgeadam.teknikservis.model.ProposalDTO;
import com.bilgeadam.teknikservis.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final MessageSource messageSource;

    @Autowired
    public ProposalController(ProposalRepository proposalRepository, MessageSource messageSource) {
        this.proposalRepository = proposalRepository;
        this.messageSource= messageSource;
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
    public ResponseEntity<String> deleteById(Locale locale, @PathVariable long id) {
        Object[] params = new Object[1];
        params[0] = id;
        boolean result = proposalRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok(messageSource.getMessage("proposal.delete.success" , params ,locale));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("proposal.delete.error", null, locale));
    }

    @PostMapping("/user/saveWithDTO")
    public ResponseEntity<String> save(Locale locale, @RequestBody ProposalDTO proposalDTO) {
        boolean result = proposalRepository.saveWithDTO(proposalDTO);
        if (result)
            return ResponseEntity.ok(messageSource.getMessage("proposal.saveDto.success" , null , locale));
        else
            return ResponseEntity.badRequest().build();
    }
    @PostMapping("/user/saveWithId")
    public ResponseEntity<String> save(Locale locale,@RequestBody Proposal proposal) {
        boolean result = proposalRepository.saveWithId(proposal);
        if (result)
            return ResponseEntity.ok(messageSource.getMessage("proposal.saveId.success", null , locale));
        else
            return ResponseEntity.badRequest().build();
    }
    @GetMapping(path = "admin/getalldto",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProposalAdminDto>> getalldto()
    {
        return ResponseEntity.ok(proposalRepository.getAllDTO());
    }


    @PutMapping(path = "/admin/updatetruestatus/{id}")
    public ResponseEntity<String> updateStatus(Locale locale,@PathVariable(name = "id") long id)
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
                return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.update.error", null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.update.error", null, locale));
        }
    }
    @PutMapping(path = "/admin/updatefalsestatus/{id}")
    public ResponseEntity<String> updateFalseStatus(Locale locale ,@PathVariable(name = "id") long id)
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
                return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.updateFalse.error", null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.updateFalse.error", null, locale));
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
