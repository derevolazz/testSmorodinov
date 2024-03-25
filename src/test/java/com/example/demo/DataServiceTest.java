package com.example.demo;

import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;
import com.example.demo.repository.DataRepository;
import com.example.demo.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private DataService dataService;

    private DataEntity existingEntity;
    private final int existingKey = 1;
    private final int nonExistingKey = 2;

    @BeforeEach
    void setUp() {
        existingEntity = new DataEntity();
        existingEntity.setId(existingKey);
        existingEntity.setData("Data");
    }

    @Test
    void testGetDataByKeyWhenKeyExistsThenReturnDataResponse() {
        when(dataRepository.findById(existingKey)).thenReturn(java.util.Optional.of(existingEntity));

        DataResponse result = dataService.getDataByKey(existingKey);

        assertEquals("ok", result.getResponse());
        assertEquals(Integer.toString(existingKey), result.getRequest());
        assertEquals(existingEntity, result.getData());
    }

    @Test
    void testGetDataByKeyWhenKeyDoesNotExistThenReturnError() {
        when(dataRepository.findById(nonExistingKey)).thenReturn(java.util.Optional.empty());

        DataResponse result = dataService.getDataByKey(nonExistingKey);

        assertEquals("error", result.getResponse());
        assertEquals(Integer.toString(nonExistingKey), result.getRequest());
        assertNull(result.getData());
    }

    @Test
    void testCreateDataWhenDataEntitySavedThenReturnSameEntity() {
        when(dataRepository.save(Mockito.any(DataEntity.class))).thenReturn(existingEntity);

        DataEntity result = dataService.createData(existingEntity);

        assertEquals(existingEntity, result);
    }
}