package fi.academy.springauth.photoShoot;

import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoshootPlanService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PhotoshootPlanRepository photoshootPlanRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    /**
     * Metodi poistaa yhden suunnitelman varmistuttuaan siitä, että käyttäjä on poistamassa omaa suunnitelmaansa.
     * Varmistuminen tapatuu vertaamalla suunnitelman kirjoittajan nimeä käyttäjän nimeen, joiden on oltava samat.
     */
    public ResponseEntity<?> deletePlan(long id, Principal user) {
        Optional<PhotoshootPlanEntity> currentPlan = photoshootPlanRepository.findById(id);
        if (currentPlan.get().getCreator().getUsername().equals(user.getName())) {
            photoshootPlanRepository.delete(currentPlan.get());
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
    }
    /**
     * Muokkaa jo luotua suunnitelmaa
     * Polkuna käytetään id:tä. Suunnitelman luojan nimeä verrataan kirjautuneen käyttäjän nimeen, jotta varmistutaan,
     * että muokkaaja on suunnitelman luoja (voi olla vain yksi käyttäjä samalla usernamella). Haetaan ensin oikea suunnitelma
     * annetulla id:llä. Palauttaa muokatun suunnitelman ja OK statuksen mikäli muokkaus on sallittu.
     */
    public ResponseEntity<?> editPlan(long id, @RequestBody PhotoshootPlanEntity plan, Principal user) {
        Optional<PhotoshootPlanEntity> currentPlan = photoshootPlanRepository.findById(id);
       // Optional<AppUserEntity> currentUser = appUserRepository.findByUsername(user.getName());
        if (currentPlan.get().getCreator().getUsername().equals(user.getName())){
            AppUserEntity creator = currentPlan.get().getCreator();
            plan.setId(id);
            plan.setCreator(creator);
            photoshootPlanRepository.save(plan);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> findOneById(long id, Principal user){
        Optional<PhotoshootPlanEntity> currentPlan = photoshootPlanRepository.findById(id);
        if (!currentPlan.isPresent()){
            return new ResponseEntity<>("Virhe: annetulla id:llä ei löytynyt suunnitelmaa", HttpStatus.NOT_FOUND);
        }
        if (currentPlan.get().getCreator().getUsername().equals(user.getName())){
            return  new ResponseEntity<>(currentPlan.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}