package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 脉象模型

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pulse {
    private boolean weak;
    private boolean rapid;
    private boolean slippery;
    private boolean wiry;
    private boolean thin;
}

