package com.zhongyi.droolspro.service;

import com.zhongyi.droolspro.model.Diagnosis;
import com.zhongyi.droolspro.model.Prescription;
import com.zhongyi.droolspro.model.Herb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhongyi.droolspro.model.Symptom;
import com.zhongyi.droolspro.model.Tongue;
import com.zhongyi.droolspro.model.Pulse;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import java.util.Collection;

@Service
public class DiagnosisService {

    @Autowired
    private KieSession kieSession;

    public Map<String, Object> diagnose(Symptom symptom, Tongue tongue, Pulse pulse) {
        // 创建一个新的会话实例，避免状态污染
        KieSession session = kieSession.getKieBase().newKieSession();
        
        try {
            // 插入事实对象
            session.insert(symptom);
            session.insert(tongue);
            session.insert(pulse);

            // 执行规则
            session.fireAllRules();

            // 从会话中收集诊断结果
            List<Diagnosis> diagnoses = new ArrayList<>();
            List<Prescription> prescriptions = new ArrayList<>();
            List<Herb> herbs = new ArrayList<>();
            
            // 查询会话中的所有对象
            Collection<? extends FactHandle> factHandles = session.getFactHandles();
            for (FactHandle handle : factHandles) {
                Object fact = session.getObject(handle);
                if (fact instanceof Diagnosis) {
                    diagnoses.add((Diagnosis) fact);
                } else if (fact instanceof Prescription) {
                    prescriptions.add((Prescription) fact);
                } else if (fact instanceof Herb) {
                    herbs.add((Herb) fact);
                }
            }

            // 构建结果映射
            Map<String, Object> result = new HashMap<>();
            result.put("diagnosis", diagnoses);
            result.put("prescription", prescriptions);
            result.put("herb", herbs);
            return result;
        } finally {
            // 确保会话被正确销毁
            session.dispose();
        }
    }
}

