package com.zk.entity;

public class Word {

    private Integer id;
    private String word;
    private String chinese;

    public Word() {
    }

    public Word(Integer id, String word, String chinese) {
        this.id = id;
        this.word = word;
        this.chinese = chinese;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
