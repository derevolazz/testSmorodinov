package com.example.demo.controller;

import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;
import com.example.demo.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/db2any/bykey")
public class DataController {

    private final DataService dataService;

    @GetMapping("/getData")
    public DataResponse getData(@RequestParam("key") int key) {
        return dataService.getDataByKey(key);
    }

    @PostMapping("/create")
    public DataEntity createData(@RequestBody DataEntity dataEntity) {
        return dataService.createData(dataEntity);
    }
}