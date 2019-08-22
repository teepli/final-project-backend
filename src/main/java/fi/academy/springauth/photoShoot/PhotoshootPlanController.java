package fi.academy.springauth.photoShoot;

import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageRepository;
import fi.academy.springauth.images.ImageService;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
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
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

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
     * Tallentaa kuvan tietokantaan sekä yhdistää kuvan ja suunitelman toisiinsa. Kuvia varten oltava kansio c:/kuvat
     */
    @PostMapping("")
    @Transactional
    public void createPlan (@RequestParam (required = false) String header,
                            @RequestParam (required = false) Date date,
                            @RequestParam (required = false) String location,
                            @RequestParam (required = false) String description,
                            @RequestParam (required = false) String notes,
                            @RequestParam (required = false) String participants,
                            @RequestParam(required = false) MultipartFile image,
                            Principal user)throws IOException {
        Optional<AppUserEntity> appuser = appUserRepository.findByUsername(user.getName());
        PhotoshootPlanEntity plan = new PhotoshootPlanEntity();
        plan.setHeader(header);
        plan.setDate(date);
        plan.setLocation(location);
        plan.setDescription(description);
        plan.setNotes(notes);
        plan.setParticipants(participants);
        plan.setCreator(appuser.get());
        if(image != null){
            ImageEntity i = imageService.createImage(image);
            i.setPhotoshoot(plan);
            //plan.getReferencePictures().add(i);
        }
        photoshootPlanRepository.save(plan);


    }
    /*@PutMapping("/{id}/pictures")

    public ResponseEntity<?> addPictures(@PathVariable long id, @RequestParam(required = false) MultipartFile image,
                                         @RequestParam(required = false) MultipartFile image2,
                                         @RequestParam(required = false) MultipartFile image3,
                                         @RequestParam(required = false) MultipartFile image4,
                                         @RequestParam(required = false) MultipartFile image5, Principal user){
        Optional<PhotoshootPlanEntity> currentPlan = photoshootPlanRepository.findById(id);
        Optional<AppUserEntity> currentUser = appUserRepository.findByUsername(user.getName());
        if (currentPlan.get().getId() == id){
        PhotoshootPlanEntity plan = currentPlan.get();
            if (currentPlan.get().getCreator().getUsername().equals(user.getName())) {
                AppUserEntity creator = currentPlan.get().getCreator();
                plan.setId(id);
                plan.setCreator(creator);
                photoshootPlanRepository.save(plan);
                return new ResponseEntity<>(plan, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
    }*/


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
