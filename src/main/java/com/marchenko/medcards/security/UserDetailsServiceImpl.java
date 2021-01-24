package com.marchenko.medcards.security;


import com.marchenko.medcards.models.Doctor;
import com.marchenko.medcards.models.Patient;
import com.marchenko.medcards.service.DoctorService;
import com.marchenko.medcards.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public UserDetailsServiceImpl(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
       try {
           Doctor user = doctorService.findByLogin(login);
           return SecurityUser.fromUser(user);
       } catch (UsernameNotFoundException usernameNotFoundException){
           Patient user = patientService.findByLogin(login);
           return SecurityUser.fromUser(user);
       }

    }
}
