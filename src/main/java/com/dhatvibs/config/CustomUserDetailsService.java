/*
 * package com.dhatvibs.config;
 * 
 * //import com.dhatvibs.modules.user.entity.User; //import
 * com.dhatvibs.modules.user.repository.UserRepository; import
 * lombok.RequiredArgsConstructor; import
 * org.springframework.security.core.userdetails.*; import
 * org.springframework.stereotype.Service;
 * 
 * import com.dhatvibs.modules.auth.repository.UserRepository; import
 * com.dhatvibs.modules.auth.entity.*; import
 * com.dhatvibs.modules.auth.entity.User;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class CustomUserDetailsService implements
 * UserDetailsService {
 * 
 * private final UserRepository userRepository;
 * 
 * @Override public UserDetails loadUserByUsername(String userCode) throws
 * UsernameNotFoundException {
 * 
 * User user = userRepository.findByUserCode(userCode) .orElseThrow(() -> new
 * UsernameNotFoundException("User not found"));
 * 
 * return org.springframework.security.core.userdetails.User .builder()
 * .username(user.getUserCode()) .password(user.getPassword())
 * .roles(user.getRole()) .build(); } }
 */