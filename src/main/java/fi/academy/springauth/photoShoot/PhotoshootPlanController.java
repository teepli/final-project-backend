package fi.academy.springauth.photoShoot;

import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/plans")
public class PhotoshootPlanController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PhotoshootPlanRepository photoshootPlanRepository;

    @Autowired
    private PhotoshootPlanService photoshootPlanService;

    /*@GetMapping("")
    public List<PhotoshootPlanEntity> getAllPlan() {
        return photoshootPlanRepository.findAll();
    }*/

    /**
     * Hakee kaikki kirjautuneena olevan käyttäjän luomat suunnitelmat.
     * Kirjautuneena oleva käyttäjä saadaan Principalin avulla, palautetaan ko käyttäjän suunnitelmalista
     */
    @GetMapping("")
    public List<PhotoshootPlanEntity> findbyCreator(Principal user){
        Optional<AppUserEntity> appuser = appUserRepository.findByUsername(user.getName());
        return photoshootPlanRepository.findByCreator(appuser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOnebyId(@PathVariable long id, Principal user){
        return photoshootPlanService.findOneById(id, user);
    }

    /**
     * Luo uuden suunnitelman
     * Hakee kirjautuneen käyttäjän Principalin avulla ja asettaa käyttäjän suunnitelman luojaksi.
     */
    @PostMapping("")
    public void createPlan (@RequestBody PhotoshootPlanEntity plan, Principal user){
        Optional<AppUserEntity> appuser = appUserRepository.findByUsername(user.getName());
        plan.setCreator(appuser.get());
        photoshootPlanRepository.save(plan);
    }
    /**
     * Poistaa suunnitelman. Ks PhotoshootPlanService.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable long id, Principal user) {
        return photoshootPlanService.deletePlan(id, user);
    }
    /**
     * Muokkaa luotua suunnitelmaa. Ks PhotoshootPlanService.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editPlan(@PathVariable long id, @RequestBody PhotoshootPlanEntity plan, Principal user) {
        return photoshootPlanService.editPlan(id, plan, user);
    }

}
