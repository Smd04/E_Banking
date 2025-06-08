package com.projet.project_e_banking.Controller;



import com.projet.project_e_banking.Dto.LoginRequest;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200")
public class test {

    @GetMapping("/")
    public String config() {
        return "config work well";
    }

    @GetMapping("/config")
    public String config2() {
        return "security work well";
    }

    @PostMapping("/config2")
    public String testpost(@RequestBody LoginRequest loginRequest){
        return loginRequest.getUsername()+loginRequest.getPassword();
    }

}

