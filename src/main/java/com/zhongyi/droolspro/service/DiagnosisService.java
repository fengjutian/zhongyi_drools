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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import com.zhongyi.droolspro.model.Symptom;
import com.zhongyi.droolspro.model.Tongue;
import com.zhongyi.droolspro.model.Pulse;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiagnosisService {
    // 诊断服务：将症状/舌象/脉象插入规则引擎，执行规则，汇总诊断/处方/药材

    @Autowired
    private KieSession kieSession;

    /**
     * 诊断入口
     * @param symptom 症状事实
     * @param tongue  舌象事实
     * @param pulse   脉象事实
     * @return 返回诊断（按置信度去重/排序/过滤）、处方、药材，以及不确定性标记
     */
    public Map<String, Object> diagnose(Symptom symptom, Tongue tongue, Pulse pulse) {
        KieSession session = kieSession.getKieBase().newKieSession();
        Logger logger = LoggerFactory.getLogger("rules");
        session.setGlobal("log", logger); // 设置全局日志，供 DRL 中记录命中
        
        try {
            // 插入事实对象（使规则可匹配）
            session.insert(symptom);
            session.insert(tongue);
            session.insert(pulse);

            // 执行所有规则
            session.fireAllRules();

            // 从会话中收集诊断结果
            List<Diagnosis> diagnoses = new ArrayList<>();
            List<Prescription> prescriptions = new ArrayList<>();
            List<Herb> herbs = new ArrayList<>();
            
            // 查询会话中的所有对象（诊断/处方/药材）
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

            // 去重：同一证型仅保留最高置信度
            Map<String, Diagnosis> bestByPattern = new HashMap<>();
            for (Diagnosis d : diagnoses) {
                Diagnosis prev = bestByPattern.get(d.getPattern());
                if (prev == null || d.getConfidence() > prev.getConfidence()) {
                    bestByPattern.put(d.getPattern(), d);
                }
            }
            List<Diagnosis> deduped = new ArrayList<>(bestByPattern.values());
            deduped.sort(Comparator.comparingDouble(Diagnosis::getConfidence).reversed()); // 置信度降序
            double threshold = 0.80; // 过滤阈值：低于此值的诊断不计入 filtered
            List<Diagnosis> filtered = deduped.stream()
                    .filter(d -> d.getConfidence() >= threshold)
                    .collect(Collectors.toList());

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("diagnosis", filtered.isEmpty() ? deduped : filtered); // 若无高置信度结果，则返回去重后的全部
            result.put("prescription", prescriptions);
            result.put("herb", herbs);
            result.put("uncertain", filtered.isEmpty()); // 不确定性判断：无任何达到阈值的诊断
            return result;
        } finally {
            // 确保会话被正确销毁
            session.dispose();
        }
    }
}

