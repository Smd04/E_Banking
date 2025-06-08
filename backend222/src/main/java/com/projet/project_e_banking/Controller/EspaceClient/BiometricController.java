package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.QRPaimentDTO;
import com.projet.project_e_banking.Dto.EspaceClient.RegisterCrentialRequest;
import com.projet.project_e_banking.Dto.EspaceClient.VerifyRequest;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Model.ModelPaymentBiometrique.WebAuthCredential;
import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.EspaceClient.TransactionRepository;
import com.projet.project_e_banking.Repository.EspaceClient.WebAuthnCredentialRepository;
import com.projet.project_e_banking.Service.EspaceBanque.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/biometric")
public class BiometricController {


    @Autowired
    private WebAuthnCredentialRepository repo;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    private SecureRandom secureRandom = new SecureRandom();


    @GetMapping("/check-credential")
    public ResponseEntity<?> checkCredential(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        User user = userService.findByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(403).body("Utilisateur non trouvé");
        }
        try{
            Long userId = user.getId();
            boolean exists = repo.findByUserId(userId).isPresent();
            byte[] challenge = new byte[32];
            secureRandom.nextBytes(challenge);
            String base64Challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(challenge);
            session.setAttribute("challenge", base64Challenge);

            return ResponseEntity.ok(Map.of(
                    "exists", exists,
                    "challenge", base64Challenge
            ));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Erreur lors de  check credential :"+e.getMessage());
        }

    }
    /*@GetMapping("/webauthn/challenge")
    public  ResponseEntity<Map<String,String>> getChallenge(HttpSession session) {
        byte[] challenge = new byte[32];
        secureRandom.nextBytes(challenge);
        String base64Challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(challenge);
        session.setAttribute("challenge", base64Challenge);
        Map<String,String> response = new HashMap<>();
          response.put("challenge", base64Challenge);
          return ResponseEntity.ok(response);
    }*/

    @PostMapping("register-credential")
    public ResponseEntity<?> registerCredential(@AuthenticationPrincipal UserDetails userDetails,@RequestBody RegisterCrentialRequest req){
        if(userDetails == null){
            return ResponseEntity.status(403).body("non connectée");
        }
        User user = userService.findByUsername(userDetails.getUsername());
        Long userId = user.getId();
        WebAuthCredential cred = new WebAuthCredential();
         try{
            cred.setCredentialId(req.getCredentialId());
            cred.setUserId(userId);
            cred.setPublicKey(Base64.getUrlDecoder().decode(req.getPublicKey()));
            repo.save(cred);
            return ResponseEntity.ok("Public Key registred");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Erreur lors de register credential :"+e.getMessage());
        }
    }




    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyRequest request) {
        // 1. Récupérer la clé publique stockée
        Optional<WebAuthCredential> opt = repo.findById(request.getCredentialId());
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Clé non enregistrée");

        WebAuthCredential cred = opt.get();
        byte[] publicKeyBytes = cred.getPublicKey();
        String challengeSent = (String) session.getAttribute("challenge");
        if (!request.getChallenge().equals(challengeSent)) {
            return ResponseEntity.status(400).body("Challenge invalide");
        }

        try {
            // 2. Reconstruire la clé publique au format ECPublicKey
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

            // 3. Préparer le contenu signé (clientDataJSON + authenticatorData, etc.)
            byte[] dataToVerify = Base64.getUrlDecoder().decode(request.getClientDataJSON()); // contenu signé
            byte[] signature = Base64.getUrlDecoder().decode(request.getSignature());

            // 4. Vérifier la signature
            Signature sig = Signature.getInstance("SHA256withECDSA");
            sig.initVerify(publicKey);
            sig.update(dataToVerify);

            boolean isValid = sig.verify(signature);
            if (isValid) {
                return ResponseEntity.ok("Paiement validé !");
            } else {
                return ResponseEntity.status(401).body("Échec de la validation biométrique");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la vérification");
        }
    }
    @PostMapping("/effectuer-paiment")
    public ResponseEntity<?>effectuerPiment(@RequestBody QRPaimentDTO paiment){
        Transaction t = new Transaction();
     Optional<Account> account = accountRepository.findByAccountNumber(paiment.getCompteSource());
        account.get().setBalance(account.get().getBalance()-paiment.getMontant());
        t.setAccount(account.orElse(null));
        t.setCompteDest(paiment.getCompteDest());
        t.setAmount(paiment.getMontant());
        t.setDescription("Paiment par QR code");
        t.setStatus("valide");
        t.setTimestamp(LocalDateTime.now());
        try{
            transactionRepository.save(t);
            accountRepository.save(account.orElse(null));
        }
       catch (Exception e){
            System.out.println("Erreur lors de la effectuer paiment");
            e.printStackTrace();
       }

        return  ResponseEntity.ok().body("Paiment Effectué avec succées");
    }

}
