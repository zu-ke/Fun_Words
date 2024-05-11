package com.zk.web;

import com.google.gson.Gson;
import com.zk.entity.Msg;
import com.zk.entity.Word;
import com.zk.service.WordService;
import com.zk.service.impl.WordServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getWords")
public class WordServlet extends BasicServlet{

    private WordService wordService = new WordServiceImpl();

    protected void getWords(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String table = request.getParameter("d");
        if (table == null || table.length() == 0 || "".equals(table)) {
            table = "level_6_vocabulary";
        }
        List<Word> wordList = wordService.getWordsCosineSimilarity(table);
        if (wordList.size() <= 3){
            Word word1 = new Word(1, "恭喜学完本难度单词", "恭喜学完本难度单词");
            Word word2 = new Word(2, "恭喜学完本难度单词", "恭喜学完本难度单词");
            Word word3 = new Word(3, "恭喜学完本难度单词", "恭喜学完本难度单词");
            Word word4 = new Word(4, "恭喜学完本难度单词", "恭喜学完本难度单词");
            wordList.add(word1);
            wordList.add(word2);
            wordList.add(word3);
            wordList.add(word4);
        }
        Msg msg = Msg.success().add("wordList", wordList);
        Gson gson = new Gson();
        String resultJson = gson.toJson(msg);
        response.getWriter().write(resultJson);
    }
}
