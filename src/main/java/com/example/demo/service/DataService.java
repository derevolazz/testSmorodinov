package com.example.demo.service;

import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;
import com.example.demo.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;

    public DataResponse getDataByKey(int key) {
        DataEntity record = dataRepository.findById(key).orElse(null);

        DataResponse response = new DataResponse();
        response.setData(record);
        response.setResponse(record != null ? "ok" : "error");
        response.setRequest(Integer.toString(key));

        return response;
    }

    public DataEntity createData(DataEntity dataEntity) {
        return dataRepository.save(dataEntity);
    }
}