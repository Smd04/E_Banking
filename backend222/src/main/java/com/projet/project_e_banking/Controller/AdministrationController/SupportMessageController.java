package com.projet.project_e_banking.Controller.AdministrationController;


import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Dto.AdministrationDto.SupportMessageDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;
import com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl.SupportMessageDaoImpl;
import com.projet.project_e_banking.Service.Mapper.AdministrationMapper.DeviseMapper;
import com.projet.project_e_banking.Service.Mapper.AdministrationMapper.SupportMessageMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/supportmessages")
@CrossOrigin(origins = "http://localhost:4200")
public class SupportMessageController {

    private final SupportMessageDaoImpl supportMessageDaoimpl;

    public SupportMessageController(SupportMessageDaoImpl supportMessageDaoimpl) {
        this.supportMessageDaoimpl = supportMessageDaoimpl;
    }

    @GetMapping("/getallmessages")
    public ResponseEntity<List<SupportMessageDto>> getallmessages(){
        List<SupportMessage> messages=supportMessageDaoimpl.getallRequestmessages();
        List<SupportMessageDto> messagedtos = messages.stream()
                .map(SupportMessageMapper::mapToDto)
                .toList();
        return ResponseEntity.ok(messagedtos);
    }

    @PutMapping("/deletemessage/{id}")
    public ResponseEntity<SupportMessageDto> deletemessage (@PathVariable("id") Long id){
        SupportMessage message=supportMessageDaoimpl.deletemessage(id);
        SupportMessageDto messagedeleted=SupportMessageMapper.mapToDto(message);
        return ResponseEntity.ok(messagedeleted);
    }

    @PostMapping("/addresponsemessage")
    public  ResponseEntity<SupportMessageDto> addresponsemsg(@RequestBody SupportMessageDto responsemsgdto){
        SupportMessage responsemsg= supportMessageDaoimpl.addResponsemessage(responsemsgdto);
        return new ResponseEntity<>(SupportMessageMapper.mapToDto(responsemsg), HttpStatus.CREATED);
    }
}
