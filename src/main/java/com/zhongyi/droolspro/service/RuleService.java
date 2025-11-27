package com.zhongyi.droolspro.service;

import com.zhongyi.droolspro.model.User;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RuleService {

    @Autowired
    private KieSession kieSession;

    public User executeRules(User user) {
        Logger logger = LoggerFactory.getLogger("rules");
        kieSession.setGlobal("log", logger);
        kieSession.insert(user);
        kieSession.fireAllRules();

        return user;
    }
}



