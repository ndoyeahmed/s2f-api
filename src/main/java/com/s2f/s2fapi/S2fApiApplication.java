package com.s2f.s2fapi;

import com.s2f.s2fapi.fixtures.UtilisateurFixture;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class S2fApiApplication implements CommandLineRunner {

    private final UtilisateurFixture utilisateurFixture;

    public static void main(String[] args) {
        SpringApplication.run(S2fApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
       // utilisateurFixture.addDefaultAdmin();
    }
}
