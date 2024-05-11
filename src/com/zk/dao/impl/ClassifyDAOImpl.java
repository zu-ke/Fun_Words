package com.zk.dao.impl;

import com.zk.dao.BasicDAO;
import com.zk.dao.ClassifyDAO;
import com.zk.entity.Classify;

import java.util.List;

public class ClassifyDAOImpl extends BasicDAO<Classify> implements ClassifyDAO {
    @Override
    public List<Classify> getAll() {
        String sql = "select * from `classification`";
        return queryMulti(sql, Classify.class);
    }
}
