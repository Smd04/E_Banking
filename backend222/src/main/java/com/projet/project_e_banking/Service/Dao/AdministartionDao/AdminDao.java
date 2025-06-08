package com.projet.project_e_banking.Service.Dao.AdministartionDao;

import com.projet.project_e_banking.Dto.AdministrationDto.AdministratorDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;

import java.util.List;

public interface AdminDao {

    public List<Administrator>getAlladmins();
    public Administrator addadmin (AdministratorDto admin);
    public Administrator updateadmin(AdministratorDto admin);
    public Administrator deleteadmin (AdministratorDto admin);

}
