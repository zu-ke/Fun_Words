package com.zk.test;

import com.zk.dao.WordDAO;
import com.zk.dao.impl.WordDAOImpl;
import com.zk.entity.Word;
import org.junit.Test;

import java.util.List;

public class WordDAOTest {

    private WordDAO wordDAO = new WordDAOImpl();

    @Test
    public void getWords(){

        List<Word> words = wordDAO.getWords("");
        Word word = words.get(0);
        System.out.println(word);
        words.remove(0);
        Word word1 = words.get(0);
        System.out.println(word1);
    }

}
