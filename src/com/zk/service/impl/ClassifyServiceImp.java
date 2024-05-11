package com.zk.service.impl;

import com.zk.dao.ClassifyDAO;
import com.zk.dao.impl.ClassifyDAOImpl;
import com.zk.entity.Classify;
import com.zk.service.ClassifyService;

import java.util.List;

public class ClassifyServiceImp implements ClassifyService {

    private ClassifyDAO classifyDAO = new ClassifyDAOImpl();
    @Override
    public List<Classify> getAll() {
        return classifyDAO.getAll();
    }
}
