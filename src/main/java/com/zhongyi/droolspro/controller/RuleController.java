package com.zhongyi.droolspro.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhongyi.droolspro.service.RuleService;
import com.zhongyi.droolspro.model.User;

@RestController
@RequestMapping("/api/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/checkUser")
    public User checkUser(@RequestBody User user) {
        return ruleService.executeRules(user);
    }
}