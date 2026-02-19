
package com.dhatvibs.modules.auth.service;

import com.dhatvibs.modules.auth.dto.*;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    AuthResponseDto activateUser(ActivateRequestDto request,HttpSession session);

    AuthResponseDto login(LoginRequestDto request,HttpSession session);
    
    //AuthResponseDto login(LoginRequestDto request, HttpSession session) {


    void logout(HttpSession session);
}
