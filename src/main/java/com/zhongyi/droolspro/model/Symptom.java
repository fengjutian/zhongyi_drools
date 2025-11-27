package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// 症状模型

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Symptom {

    // 气虚类
    private boolean fatigue;
    private boolean shortBreath;
    private boolean spontaneousSweat;
    private boolean paleTongue;
    private boolean weakPulse;

    // 阴虚类
    private boolean nightSweats;
    private boolean dryMouth;
    private boolean thinPulse;

    // 外感风寒
    private boolean chills;
    private boolean runnyNose;
    private boolean noSweat;

    // 外感风热
    private boolean fever;
    private boolean soreThroat;
    private boolean yellowMucus;

    // 消化系统
    private boolean diarrhea;
    private boolean abdominalColdPain;
    private boolean poorAppetite;
}
