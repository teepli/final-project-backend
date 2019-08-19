package fi.academy.springauth.appUser;

import fi.academy.springauth.appUser.friend.FriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;


    public AppUserController(AppUserRepository appUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("")
    public Iterable<AppUserEntity> getUsers() {
        return appUserRepository.findAll();
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody AppUserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
    }

//    @GetMapping("/friendtest")
//    public void friendTest() {
//        AppUserEntity user = appUserRepository.findByUsername("dd");
//        AppUserEntity usertest2 = appUserRepository.findByUsername("ddd");
//        FriendEntity user1 = new FriendEntity();
//        user1.setFriendWith(appUserRepository.findByUsername("ddd"));
//        user1.setFriend(user);
//        user.getFriends().add(user1);
//        appUserRepository.save(user);
//
//        FriendEntity test2 = new FriendEntity();
//        test2.setFriendWith(user);
//        test2.setFriend(usertest2);
//        usertest2.getFriends().add(test2);
//        appUserRepository.save(usertest2);
//    }

}
