package com.zk.test;

import com.zk.dao.ClassifyDAO;
import com.zk.dao.impl.ClassifyDAOImpl;
import com.zk.entity.Classify;
import org.junit.Test;

import java.util.List;

public class ClassifyDAOTest {

    private ClassifyDAO classifyDAO = new ClassifyDAOImpl();

    @Test
    public void getAll(){
        List<Classify> all = classifyDAO.getAll();
        for (Classify classify : all) {
            System.out.println(classify);
        }
    }

}
