package com.zhongyi.droolspro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class DroolsConfig {

   private static final Logger log = LoggerFactory.getLogger(DroolsConfig.class);

   private static final String RULES_PATH = "rules/";
   private static final String DRL_SUFFIX = ".drl";

   @Bean
   public KieSession kieSession() {
       KieServices ks = KieServices.Factory.get();
       KieHelper helper = new KieHelper();

       try {
           List<String> ruleFiles = getRuleFiles();
           log.info("加载规则文件: {}", ruleFiles);
           for (String ruleFile : ruleFiles) {
               String resourcePath = RULES_PATH + ruleFile;
               Resource resource = ks.getResources().newClassPathResource(resourcePath);
               log.info("添加规则资源: {}", resourcePath);
               helper.addResource(resource, ResourceType.DRL);
           }

           Results results = helper.verify();
           if (results.hasMessages(Message.Level.ERROR)) {
               log.error("Drools 规则加载失败: {}", results.getMessages(Message.Level.ERROR));
               throw new RuntimeException("Drools 规则加载失败:\n" + results);
           }

           log.info("Drools KieSession 创建成功");
           return helper.build().newKieSession();
       } catch (Exception e) {
           log.error("Drools会话初始化失败", e);
           throw new RuntimeException("Drools会话初始化失败:", e);
       }
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
