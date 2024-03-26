package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataResponse {

    private DataEntity data;

    private String response;

    private String request;

    public DataResponse(String response, DataEntity data, String request) {
        this.response = response;
        this.data = data;
        this.request = request;
    }
}