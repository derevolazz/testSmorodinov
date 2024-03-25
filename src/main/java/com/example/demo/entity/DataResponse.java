package com.example.demo.entity;

import lombok.Data;

@Data
public class DataResponse {
    private DataEntity data;
    private String response;
    private String request;
}