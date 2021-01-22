package com.marchenko.medcards.security;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.repository.DoctorRepository;
import com.marchenko.medcards.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DoctorService doctorService;

    @Autowired
    public UserDetailsServiceImpl(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Doctor user = doctorService.findByLogin(login);
        return SecurityUser.fromUser(user);
    }
}
