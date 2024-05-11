package com.zk.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.*;

public class ZkUtils {

    /*map集合逆序排序*/

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.<K, V>comparingByValue().reversed());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


    /*余弦相似度匹配算法*/
    public static double cosineSimilarity(String word1, String word2) {
        // 创建两个映射，用于存储每个单词中字符的频率
        Map<Character, Integer> freq1 = new HashMap<>();
        Map<Character, Integer> freq2 = new HashMap<>();

        // 计算第一个单词中每个字符的频率
        for (char ch : word1.toCharArray()) {
            freq1.put(ch, freq1.getOrDefault(ch, 0) + 1);
        }

        // 计算第二个单词中每个字符的频率
        for (char ch : word2.toCharArray()) {
            freq2.put(ch, freq2.getOrDefault(ch, 0) + 1);
        }

        // 计算点积
        double dotProduct = 0.0;
        for (char ch : freq1.keySet()) {
            dotProduct += freq1.get(ch) * freq2.getOrDefault(ch, 0);
        }

        // 计算两个字符向量的模
        double norm1 = 0.0;
        for (int val : freq1.values()) {
            norm1 += val * val;
        }
        norm1 = Math.sqrt(norm1);

        double norm2 = 0.0;
        for (int val : freq2.values()) {
            norm2 += val * val;
        }
        norm2 = Math.sqrt(norm2);

        // 计算余弦相似度
        return dotProduct / (norm1 * norm2);
    }

    //public static void main(String[] args) {
    //    /*解读：
    //    *在 `main` 函数中，我们有两个单词 "hello" 和 "hola"。我们想要计算这两个单词的余弦相似度。按照上面的代码，我们会执行以下步骤：
    //    1. **创建字符频率映射（Maps）**：
    //    * 我们为每个单词创建一个映射，以存储每个字符在单词中出现的次数。
    //    * 对于 "hello"，映射将是 {'h': 1, 'e': 1, 'l': 2, 'o': 1}。对于 "hola"，映射将是 {'h': 1, 'o': 1, 'l': 1, 'a': 1}。
    //    *
    //    2. **计算点积（Dot Product）**：
    //    * 我们遍历第一个单词的映射，并将每个字符的频率与第二个单词中该字符的频率相乘（如果第二个单词中存在该字符），
    //    * 然后将所有结果相加。在这个例子中，只有 'h'、'o' 和 'l' 在两个单词中都出现，因此点积是：$1*1 + 1*1 + 2*1 = 4$。
    //    *
    //    3. **计算模长（Norms）**：
    //    * 我们计算每个单词的模长。模长是映射中频率的平方和的平方根。对于 "hello"，
    //    * 模长是 $\sqrt{1^2 + 1^2 + 2^2 + 1^2} = \sqrt{1 + 1 + 4 + 1} = \sqrt{7}$。
    //    * 对于 "hola"，模长是 $\sqrt{1^2 + 1^2 + 1^2 + 1^2} = \sqrt{1 + 1 + 1 + 1} = \sqrt{4}$。
    //    *
    //    4. **计算余弦相似度（Cosine Similarity）**：
    //    * 最后，我们使用点积和模长来计算余弦相似度：$\frac{4}{\sqrt{7} * \sqrt{4}} = \frac{4}{\sqrt{28}} = \frac{4}{2\sqrt{7}} = \frac{2}{\sqrt{7}} \approx 0.7559$。
    //    这个值表示 "hello" 和 "hola" 这两个单词在字符分布上的相似程度。
    //    *
    //    * 由于这两个单词有几个相同的字符，并且这些字符出现的频率也相对接近，所以得到了一个相对较高的余弦相似度值。
    //    * 但是，这个值并不反映单词的实际含义或语言学上的相似性，它仅仅是基于字符层面上的一个数学度量。
    //    * */
    //    String word1 = "hello";
    //    String word2 = "hola";
    //
    //    System.out.println("The cosine similarity between " + word1 + " and " + word2 + " is: "
    //            + cosineSimilarity(word1, word2));
    //}

}
