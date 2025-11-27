package com.zhongyi.droolspro.model;

public class FactBuilder {
    // 症状信息（患者主诉与体征）
    private Symptom symptom;
    // 舌象信息（舌质、舌苔等）
    private Tongue tongue;
    // 脉象信息（脉位、脉形等）
    private Pulse pulse;
    
    // 显式添加getter方法
    public Symptom getSymptom() {
        return symptom;
    }
    
    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }
    
    public Tongue getTongue() {
        return tongue;
    }
    
    public void setTongue(Tongue tongue) {
        this.tongue = tongue;
    }
    
    public Pulse getPulse() {
        return pulse;
    }
    
    public void setPulse(Pulse pulse) {
        this.pulse = pulse;
    }
}

