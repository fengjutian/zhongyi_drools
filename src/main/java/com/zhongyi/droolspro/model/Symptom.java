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
    private Boolean fatigue;
    private Boolean shortBreath;
    private Boolean spontaneousSweat;
    private Boolean paleTongue;
    private Boolean weakPulse;

    // 阴虚类
    private Boolean nightSweats;
    private Boolean dryMouth;
    private Boolean thinPulse;

    // 外感风寒
    private Boolean chills;
    private Boolean runnyNose;
    private Boolean noSweat;

    // 外感风热
    private Boolean fever;
    private Boolean soreThroat;
    private Boolean yellowMucus;

    // 消化系统
    private Boolean diarrhea;
    private Boolean abdominalColdPain;
    private Boolean poorAppetite;
}
