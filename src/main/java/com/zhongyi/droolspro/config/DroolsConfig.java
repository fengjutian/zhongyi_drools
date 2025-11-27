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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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
           List<String> rulePaths = getRuleResourcePaths();
           log.info("加载规则文件: {}", rulePaths);
           for (String resourcePath : rulePaths) {
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

   private List<String> getRuleResourcePaths() throws IOException {
       List<String> rulePaths = new ArrayList<>();
       PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
       org.springframework.core.io.Resource[] resources = resolver.getResources("classpath*:" + RULES_PATH + "*" + DRL_SUFFIX);
       for (org.springframework.core.io.Resource res : resources) {
           String filename = res.getFilename();
           if (filename != null && filename.endsWith(DRL_SUFFIX)) {
               rulePaths.add(RULES_PATH + filename);
           }
       }
       return rulePaths;
   }
}
