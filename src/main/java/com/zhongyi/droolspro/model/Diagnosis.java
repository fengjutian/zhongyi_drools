package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// 辨证结果

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {
    private String pattern;   // 证型，如 气虚 / 阴虚 / 风寒 / 风热
    private String reason;    // 依据
    private double confidence;  // （可选）模型或推理置信度
}

