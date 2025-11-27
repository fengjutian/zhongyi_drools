package com.zhongyi.droolspro.service;

import com.zhongyi.droolspro.model.User;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    @Autowired
    private KieSession kieSession;

    public User executeRules(User user) {
        kieSession.insert(user);
        kieSession.fireAllRules();
        return user;
    }
}



