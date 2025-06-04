package com.projet.project_e_banking.Controller.AdministrationController;


import com.projet.project_e_banking.Dto.AdministrationDto.AdministratorDto;
import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl.AdminDaoImpl;
import com.projet.project_e_banking.Service.Mapper.AdministrationMapper.AdministratorMapper;
import com.projet.project_e_banking.Service.Mapper.AdministrationMapper.DeviseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminDaoImpl admindaoimpl ;

    public AdminController(AdminDaoImpl admindaoimpl) {
        this.admindaoimpl = admindaoimpl;
    }

    @GetMapping("/getalladmins")
    public ResponseEntity<List<AdministratorDto>> getalladmins(){
        List<Administrator> admins= admindaoimpl.getAlladmins();
        List<AdministratorDto> admindtos = admins.stream()
                .map(AdministratorMapper::toDto)
                .toList();
        return ResponseEntity.ok(admindtos);
    }

    @PostMapping("/addadmin")
    public ResponseEntity<AdministratorDto> addadmin(@RequestBody AdministratorDto admindto){
        Administrator admin=admindaoimpl.addadmin(admindto);
        return new ResponseEntity<AdministratorDto>(AdministratorMapper.toDto(admin), HttpStatus.CREATED);
    }

    @PostMapping("/updateadmin")
    public ResponseEntity<AdministratorDto> updateadmin(@RequestBody AdministratorDto admindto){
        Administrator admin =admindaoimpl.updateadmin(admindto);
        return ResponseEntity.ok(AdministratorMapper.toDto(admin));
    }

    @PostMapping("/deleteadmin")
    public ResponseEntity<AdministratorDto> deleteadmin(@RequestBody AdministratorDto admindto){
        Administrator admin =admindaoimpl.deleteadmin(admindto);
        return ResponseEntity.ok(AdministratorMapper.toDto(admin));
    }
}
