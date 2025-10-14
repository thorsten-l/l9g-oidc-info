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
package l9g.oidc.info.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@Service
@Slf4j
public class SessionIndexService
{
  private final Map<String, String> sidToHttpSession = new ConcurrentHashMap<>();

  private final Map<String, Set<String>> subToSessions = new ConcurrentHashMap<>();

  public void bindSidToHttpSession(String sid, String httpSessionId)
  {
    sidToHttpSession.put(sid, httpSessionId);
  }

  public String findHttpSessionIdBySid(String sid)
  {
    return sidToHttpSession.get(sid);
  }

  public void bindSubjectToHttpSession(String sub, String httpSessionId)
  {
    subToSessions.computeIfAbsent(sub, k -> ConcurrentHashMap.newKeySet()).add(httpSessionId);
  }

  public Set<String> findHttpSessionIdsBySubject(String sub)
  {
    return subToSessions.getOrDefault(sub, Set.of());
  }

  public void removeByHttpSessionId(String httpSessionId)
  {
    sidToHttpSession.entrySet().removeIf(e -> httpSessionId.equals(e.getValue()));
    subToSessions.values().forEach(set -> set.remove(httpSessionId));
  }

}
