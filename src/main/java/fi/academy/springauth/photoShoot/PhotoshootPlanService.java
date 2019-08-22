package fi.academy.springauth.photoShoot;

import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
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
    @Autowired
    private ImageService imageService;


    public ResponseEntity<?> createPlan (@RequestParam (required = false) String header,
                            @RequestParam (required = false) Date date,
                            @RequestParam (required = false) String location,
                            @RequestParam (required = false) String description,
                            @RequestParam (required = false) String notes,
                            @RequestParam (required = false) String participants,
                            @RequestParam(required = false) MultipartFile image,
                            Principal user)throws IOException {
        Optional<AppUserEntity> appuser = appUserRepository.findByUsername(user.getName());
        if (appuser.get().getUsername().equals(user.getName())) {
            PhotoshootPlanEntity plan = new PhotoshootPlanEntity();
            plan.setHeader(header);
            plan.setDate(date);
            plan.setLocation(location);
            plan.setDescription(description);
            plan.setNotes(notes);
            plan.setParticipants(participants);
            plan.setCreator(appuser.get());
            if (image != null) {
                ImageEntity i = imageService.createImage(image);
                i.setPhotoshoot(plan);
                //plan.getReferencePictures().add(i);
            }
            photoshootPlanRepository.save(plan);
            URI loc = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port(8080)
                    .path("/plans/{id}")
                    .buildAndExpand(plan.getId())
                    .toUri();
            return ResponseEntity.created(loc).build();
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
    }

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
     * Muokkaa jo luotua suunnitelmaa muiden tietojen, paitsi kuvien osalta.
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

    /** Kuvien lisäys olemassaolevalle suunnitelmalle. Kuvia voi lisätä max5 ja polkuparametrina käytetään suunnitelman
     * id:tä. Jotta tärkeitä tietoja ei häviä päivityksen yhteydessä, talletetaan id ja sunnitelman luoja*/
    public ResponseEntity<?> addPictures(@PathVariable long id, @RequestParam(required = false) MultipartFile image1,
                                         @RequestParam(required = false) MultipartFile image2,
                                         @RequestParam(required = false) MultipartFile image3,
                                         @RequestParam(required = false) MultipartFile image4,
                                         @RequestParam(required = false) MultipartFile image5, Principal user) throws IOException {
    Optional<PhotoshootPlanEntity> currentPlan = photoshootPlanRepository.findById(id);
    Optional<AppUserEntity> currentUser = appUserRepository.findByUsername(user.getName());
        if (currentPlan.get().getId() == id){
        PhotoshootPlanEntity plan = currentPlan.get();
        if (currentPlan.get().getCreator().getUsername().equals(user.getName())) {
            AppUserEntity creator = currentPlan.get().getCreator();
            plan.setId(id);
            plan.setCreator(creator);
            if(image1 != null){
                ImageEntity a = imageService.createImage(image1);
                a.setPhotoshoot(plan);
            }
            if(image2 != null){
                ImageEntity b = imageService.createImage(image2);
                b.setPhotoshoot(plan);
            }
            if(image3 != null){
                ImageEntity c = imageService.createImage(image3);
                c.setPhotoshoot(plan);
            }
            if(image4 != null){
                ImageEntity d = imageService.createImage(image4);
                d.setPhotoshoot(plan);
            }
            if(image5 != null){
                ImageEntity e = imageService.createImage(image5);
                e.setPhotoshoot(plan);
            }
            photoshootPlanRepository.save(plan); //tämä tarvitaan, jotta aimmin postatut kuvat säilyvät suunnitelmassa
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
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