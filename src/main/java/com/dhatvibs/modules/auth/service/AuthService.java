/*
 * package com.dhatvibs.modules.auth.service;
 * 
 * import com.dhatvibs.modules.auth.dto.*;
 * 
 * public interface AuthService {
 * 
 * AuthResponseDto activateUser(ActivateRequestDto request);
 * 
 * AuthResponseDto login(LoginRequestDto request);
 * 
 * void logout(javax.servlet.http.HttpSession session); }
 */
package com.dhatvibs.modules.auth.service;

import com.dhatvibs.modules.auth.dto.*;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    AuthResponseDto activateUser(ActivateRequestDto request);

    AuthResponseDto login(LoginRequestDto request);

    void logout(HttpSession session);
}
