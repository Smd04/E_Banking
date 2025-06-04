package com.projet.project_e_banking.Model.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double amount;
    private String status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Agent agent;

    @ManyToOne
    private Bank bank;


}

