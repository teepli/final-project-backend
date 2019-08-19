package fi.academy.springauth.photoShoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/plans")
public class PhotoshootPlanController {

    @Autowired
    private PhotoshootPlanRepository photoshootPlanRepository;

    @GetMapping("")
    public List<PhotoshootPlanEntity> getAllPlan() {
        return photoshootPlanRepository.findAll();
    }

}
