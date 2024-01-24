package com.hotel.hotelapplication.security.user;

import com.hotel.hotelapplication.repositoriy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hotel.hotelapplication.model.User;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class HotelUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found "));
        return HotelUserDetails.buildUserDetails(user);
    }



}
