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

@Service
public class DiagnosisService {

    @Autowired
    private KieSession kieSession;

    public Map<String, Object> diagnose(Symptom symptom, Tongue tongue, Pulse pulse) {

        kieSession.insert(symptom);
        kieSession.insert(tongue);
        kieSession.insert(pulse);

        List<Diagnosis> diagnoses = new ArrayList<>();
        List<Prescription> prescriptions = new ArrayList<>();
        List<Herb> herbs = new ArrayList<>();

        kieSession.setGlobal("diagnoses", diagnoses);
        kieSession.setGlobal("prescriptions", prescriptions);
        kieSession.setGlobal("herbs", herbs);

        kieSession.fireAllRules();

        Map<String, Object> result = new HashMap<>();
        result.put("diagnosis", diagnoses);
        result.put("prescription", prescriptions);
        result.put("herb", herbs);
        return result;
    }
}

