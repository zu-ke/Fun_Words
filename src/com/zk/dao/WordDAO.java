package com.zk.dao;

import com.zk.entity.Word;

import java.util.List;

public interface WordDAO {

    public List<Word> getWords(String table);
}
