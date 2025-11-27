package com.zhongyi.droolspro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongyi.droolspro.model.FactBuilder;
import com.zhongyi.droolspro.service.DiagnosisService;


@RestController
@RequestMapping("/api/tcm")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping("/diagnose")
    public Map<String, Object> diagnose(@RequestBody FactBuilder body) {
        return diagnosisService.diagnose(body.getSymptom(), body.getTongue(), body.getPulse());
    }
}

