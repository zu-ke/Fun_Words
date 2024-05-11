package com.zk.test;

import com.zk.entity.Word;
import com.zk.service.WordService;
import com.zk.service.impl.WordServiceImpl;
import com.zk.utils.ZkUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class WordServiceTest {

    private WordService wordService = new WordServiceImpl();

    @Test
    public void getWordsCosineSimilarity(){
        List<Word> wordsCosineSimilarity = wordService.getWordsCosineSimilarity("");
        for (Word word : wordsCosineSimilarity) {
            System.out.println(word);
        }

    }

}
