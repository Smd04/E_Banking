package com.projet.project_e_banking.Model.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Utilis.Role;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String employeeNumber;
    private String email;
    private String phoneNumber;
    private Role role;

    @ManyToOne
    private Bank bank;

    @OneToMany(mappedBy = "agent")
    private List<User> users;

    @OneToMany(mappedBy = "agent")
    private List<Contract> handledContracts;

    public Agent() {
    }

    public Agent(Long id, String name, String employeeNumber, String email, String phoneNumber, Role role, Bank bank, List<User> users, List<Contract> handledContracts) {
        this.id = id;
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.bank = bank;
        this.users = users;
        this.handledContracts = handledContracts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Contract> getHandledContracts() {
        return handledContracts;
    }

    public void setHandledContracts(List<Contract> handledContracts) {
        this.handledContracts = handledContracts;
    }
}
