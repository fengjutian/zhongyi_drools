package com.zhongyi.droolspro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 药材

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Herb {
    private String name;
    private String property;
    private String contraindication;
}

