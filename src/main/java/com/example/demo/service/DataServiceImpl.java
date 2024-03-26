package com.example.demo.service;

import com.example.demo.entity.DataEntity;
import com.example.demo.entity.DataResponse;
import com.example.demo.repository.DataRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final DataRepository dataRepository;

    public DataResponse getDataByKey(int key) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DataEntity> cq = cb.createQuery(DataEntity.class);
        Root<DataEntity> root = cq.from(DataEntity.class);

        DataEntity record = dataRepository.findById(key).orElse(null);
        if (record == null) {
            return new DataResponse("error: record not found", null, Integer.toString(key));
        }

        if (record.getData() != null && !record.getData().isEmpty()) {
            cq.select(cb.construct(DataEntity.class, root.get("id"), root.get("data"))).where(cb.equal(root.get("id"), key));
        } else {
            cq.select(root);
        }


        TypedQuery<DataEntity> query = entityManager.createQuery(cq);
        List<DataEntity> result = query.getResultList();

        DataResponse response = new DataResponse("error: record not found", null, Integer.toString(key));
        response.setRequest(Integer.toString(key));
        if (!result.isEmpty()) {
            response.setData(result.get(0));
            response.setResponse("ok");
        } else {
            response.setResponse("error: no data");
        }

        return response;
    }

    @Override
    public DataEntity createData(DataEntity dataEntity) {
        return dataRepository.save(dataEntity);
    }
}