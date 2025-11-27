package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 脉象模型

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pulse {
    private Boolean weak;
    private Boolean rapid;
    private Boolean slippery;
    private Boolean wiry;
    private Boolean thin;
}

