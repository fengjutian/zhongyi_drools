package com.zhongyi.droolspro.model;

public class FactBuilder {
    private Symptom symptom;
    private Tongue tongue;
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

