package com.projet.project_e_banking.Controller.AdministrationController;


import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl.DeviseDaoImpl;
import com.projet.project_e_banking.Service.Mapper.AdministrationMapper.DeviseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/devise")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviseController {

    private final DeviseDaoImpl devisedaoimpl;


    public DeviseController(DeviseDaoImpl devisedaoimpl) {
        this.devisedaoimpl = devisedaoimpl;

    }

    @PostMapping("/adddevise")
    public ResponseEntity<DeviseDto> adddevise(@RequestBody DeviseDto devisedto){
        Devise devise=devisedaoimpl.adddevise(devisedto);
        return new ResponseEntity<>(DeviseMapper.mapdevisetodto(devise),HttpStatus.CREATED);
    }

    @GetMapping("/devises")
    public ResponseEntity<List<DeviseDto>> getAllDevises() {
        List<Devise> devises = devisedaoimpl.getalldevises();
        List<DeviseDto> devisedtos = devises.stream()
                .map(DeviseMapper::mapdevisetodto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(devisedtos);
    }

    @PutMapping("/changestatus/{id}")
    public ResponseEntity<DeviseDto> updateDeviseStatus(@PathVariable("id") Long id) {
        Devise devise=devisedaoimpl.updateDevisestatus(id);
        return ResponseEntity.ok(DeviseMapper.mapdevisetodto(devise));
    }

    @PostMapping("/updatedevise")
    public ResponseEntity<DeviseDto> updateDevise(@RequestBody DeviseDto devisedto){
        System.out.println("work");
        Devise devise=devisedaoimpl.updatedevise(devisedto);
        return ResponseEntity.ok(DeviseMapper.mapdevisetodto(devise));
    }
}
