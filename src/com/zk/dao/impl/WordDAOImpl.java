package com.zk.dao.impl;

import com.zk.dao.BasicDAO;
import com.zk.dao.WordDAO;
import com.zk.entity.Word;

import java.util.List;

public class WordDAOImpl extends BasicDAO<Word> implements WordDAO {
    @Override
    public List<Word> getWords(String table) {
        String sql = "select * from `"+table+"`";
        return queryMulti(sql, Word.class);
    }
}
