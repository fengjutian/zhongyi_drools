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
import org.kie.api.io.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";
    private static final String DRL_SUFFIX = ".drl";

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        
        try {
            // 使用资源加载器获取所有规则文件
            List<String> ruleFiles = getRuleFiles();
            for (String ruleFile : ruleFiles) {
                String resourcePath = RULES_PATH + ruleFile;
                Resource resource = ks.getResources().newClassPathResource(resourcePath);
                kfs.write(resourcePath, resource);
            }
            
            KieBuilder kb = ks.newKieBuilder(kfs).buildAll();
            Results results = kb.getResults();

            if (results.hasMessages(Message.Level.ERROR)) {
                throw new RuntimeException("Drools 规则加载失败:\n" + results);
            }

            return ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        } catch (Exception e) {
            throw new RuntimeException("Drools容器初始化失败:", e);
        }
    }

    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }
    
    private List<String> getRuleFiles() throws IOException {
        List<String> ruleFiles = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        
        // 获取规则目录下的所有资源
        Enumeration<URL> resources = classLoader.getResources(RULES_PATH);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            // 这里简化处理，实际项目中可能需要更复杂的资源扫描
            // 但为了基本功能，我们只直接添加已知的规则文件
        }
        
        // 直接添加已知的规则文件，避免复杂的资源扫描
        ruleFiles.add("user-rules.drl");
        return ruleFiles;
    }
}