package com.example.demo.service;

import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;

public interface DataService {
    DataResponse getDataByKey(int key);

    DataEntity createData(DataEntity dataEntity);
}