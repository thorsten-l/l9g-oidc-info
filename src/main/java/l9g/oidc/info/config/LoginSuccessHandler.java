
/*
 * Copyright 2025 Thorsten Ludewig (t.ludewig@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package l9g.oidc.info.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import l9g.oidc.info.service.SessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{
  private final SessionStoreService sessionStore;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
    HttpServletResponse response, Authentication authentication) throws IOException,
    ServletException
  {
    log.debug("onAuthenticationSuccess");
    HttpSession session = request.getSession();
    OidcUser user = (OidcUser) authentication.getPrincipal();
    String sid = user.getIdToken().getClaimAsString("sid");
    log.debug("sid={}, session id={}", sid, session.getId());
    sessionStore.put(sid, session);
    response.sendRedirect("/app");
  }
}
