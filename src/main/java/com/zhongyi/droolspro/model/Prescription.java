package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 方剂

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    private String name;     // 四君子汤
    private String function; // 功效
}

