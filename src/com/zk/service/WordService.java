package com.zk.service;

import com.zk.entity.Word;

import java.util.List;

public interface WordService {

    public List<Word> getWordsCosineSimilarity(String table);

}
