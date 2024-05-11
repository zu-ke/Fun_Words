package com.zk.service.impl;

import com.zk.dao.WordDAO;
import com.zk.dao.impl.WordDAOImpl;
import com.zk.entity.Word;
import com.zk.service.WordService;
import com.zk.utils.ZkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordServiceImpl implements WordService {

    private WordDAO wordDAO = new WordDAOImpl();
    public static List<Word> words;
    public static String tableFlag = "";
    @Override
    public List<Word> getWordsCosineSimilarity(String table) {
        if (words == null || !tableFlag.equals(table)) {
            words = wordDAO.getWords(table);
            tableFlag = table;
        }
        Map<Word, Double> map = new HashMap<Word, Double>();
        Word word = words.get(0);
        map.put(word, 1.00);
        for (int i = 1; i < words.size(); i++) {
            double similarity = ZkUtils.cosineSimilarity(word.getWord(), words.get(i).getWord());
            map.put(words.get(i), similarity);
        }
        words.remove(0);
        Map<Word, Double> sortMap = ZkUtils.sortByValueDescending(map);
        List<Word> list = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Word, Double> entry : sortMap.entrySet()) {
            list.add(entry.getKey());
            i++;
            if (i == 4){
                break;
            }
        }
        return list;
    }
}
