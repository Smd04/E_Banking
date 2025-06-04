package com.projet.project_e_banking.Service.Dao.AdministartionDao;

import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Devise;

import java.util.List;

public interface DeviseDao {

    public Devise adddevise(DeviseDto devisedto);
    public List<Devise> getalldevises();
    public Devise updateDevisestatus(Long id );
    public Devise updatedevise(DeviseDto devisedto);
}
