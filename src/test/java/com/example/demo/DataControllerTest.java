package com.example.demo;

import com.example.demo.controller.DataController;
import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;
import com.example.demo.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(DataController.class)
public class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;

    @Test
    public void testGetDataWhenKeyIsValidThenReturnDataResponse() throws Exception {
        int validKey = 1;
        DataResponse mockResponse = new DataResponse();
        mockResponse.setRequest(Integer.toString(validKey));
        mockResponse.setResponse("ok");
        when(dataService.getDataByKey(validKey)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/db2any/bykey/getData")
                        .param("key", Integer.toString(validKey)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.request").value(Integer.toString(validKey)));
    }

    @Test
    public void testGetDataWhenKeyIsInvalidThenReturnBadRequest() throws Exception {
        String invalidKey = "abc";
        when(dataService.getDataByKey(anyInt())).thenThrow(new NumberFormatException());

        mockMvc.perform(MockMvcRequestBuilders.get("/db2any/bykey/getData")
                        .param("key", invalidKey))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateDataWhenDataEntityIsValidThenReturnDataEntity() throws Exception {
        DataEntity validDataEntity = new DataEntity();
        validDataEntity.setId(1);
        validDataEntity.setData("Data");
        when(dataService.createData(any(DataEntity.class))).thenReturn(validDataEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/db2any/bykey/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"data\":\"Data\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validDataEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("Data"));
    }
}