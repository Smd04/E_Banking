package com.projet.project_e_banking.Service.EspaceBanque;

import com.projet.project_e_banking.Dto.EspaceBanque.ContractRequest;
import com.projet.project_e_banking.Model.EspaceBanque.Contract;
import com.projet.project_e_banking.Model.EspaceBanque.Agent;
import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.BanqueRepository.ContractRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import com.projet.project_e_banking.Repository.BanqueRepository.AgentRepository;
import com.projet.project_e_banking.Repository.BanqueRepository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private BankRepository bankRepository;

    public void createContract(ContractRequest request) {
        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + request.getUserId()));

        // Vérifier que l'agent existe
        Agent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'ID: " + request.getAgentId()));

        // Vérifier que la banque existe
        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new RuntimeException("Banque non trouvée avec l'ID: " + request.getBankId()));

        // Création du contrat
        Contract contract = new Contract();
        contract.setType(request.getType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setAmount(request.getAmount());
        contract.setStatus(request.getStatus());
        contract.setUser(user);
        contract.setAgent(agent);
        contract.setBank(bank);

        contractRepository.save(contract);
    }
}