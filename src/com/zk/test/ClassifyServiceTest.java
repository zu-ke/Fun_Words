package com.zk.test;

import com.zk.entity.Classify;
import com.zk.service.ClassifyService;
import com.zk.service.impl.ClassifyServiceImp;
import org.junit.Test;

import java.util.List;

public class ClassifyServiceTest {

    private ClassifyService classifyService = new ClassifyServiceImp();

    @Test
    public void getAll(){
        List<Classify> all = classifyService.getAll();
        for (Classify classify : all) {
            System.out.println(classify);
        }
    }
}
