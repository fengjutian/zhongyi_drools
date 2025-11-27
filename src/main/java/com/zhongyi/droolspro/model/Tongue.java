package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 舌象模型

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tongue {
    private String color;   // 淡、红、绛、紫
    private String coating; // 薄白、黄腻、剥苔
    private boolean cracks; // 裂纹、无裂纹
}
