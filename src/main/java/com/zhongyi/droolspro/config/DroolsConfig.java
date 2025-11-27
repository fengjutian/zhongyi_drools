package com.zhongyi.droolspro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import java.io.File;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        // 加载 rules 目录下所有 .drl 文件
        File rulesDir = new File(getClass().getClassLoader().getResource(RULES_PATH).getFile());
        for (File ruleFile : rulesDir.listFiles()) {
            String filePath = RULES_PATH + ruleFile.getName();
            kfs.write(ResourceFactory.newClassPathResource(filePath));
        }

        KieBuilder kb = ks.newKieBuilder(kfs).buildAll();
        Results results = kb.getResults();

        if (results.hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Drools 规则加载失败:\n" + results);
        }

        return ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
    }

    @Bean
    public KieSession kieSession() {
        return kieContainer().newKieSession();
    }
}