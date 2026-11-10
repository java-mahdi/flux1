package com.kh.web.application;

import com.kh.web.model.AuthorityModel;
import com.kh.web.model.UserModel;
import com.kh.web.service.AuthorityService;
import com.kh.web.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.web.application.Consts.authority;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    @NonNull
    private UserService userService;
    @NonNull
   private AuthorityService authorityService;
    private   BCryptPasswordEncoder bCryptPasswordEncoder;


    public  void init_users() {
         Map<String, AuthorityModel> authority_map = new HashMap<>();
        authorityService.findAll()
                .subscribe(auth -> authority_map.put( Consts.authority.valueOf(auth.getName()) , auth));
       /* for (Consts.authority authrity_name : Consts.authority.values()) {
            authority_map.put(authrity_name, authorityService.save(new AuthorityModel(authrity_name.name())));
        }*/


        UserModel admin_user = new UserModel("admin", bCryptPasswordEncoder.encode("admin"),"admin" ,"admin" );
        UserModel guest_user = new UserModel("guest", bCryptPasswordEncoder.encode("guest"),"guest" ,"guest" );

        List<authority> admin_authorities = List.of(authority.ROLE_ADMIN, authority.CREATE, authority.DELETE, authority.PRINT, authority.READ);

        List<authority> guest_authorities = List.of(authority.ROLE_GUEST,  authority.PRINT, authority.READ);

        for(authority auth: admin_authorities){
            admin_user.getAuthorities().add(authority_map.get(auth));
        }
        for(authority auth: guest_authorities){
            guest_user.getAuthorities().add(authority_map.get(auth));
        }
        userService.save(admin_user);
        userService.save(guest_user);
    }

    @Override
    public void run(String... args) throws Exception {
        init_users();
    }
}