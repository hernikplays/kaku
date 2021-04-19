package cz.hernikplays.kaku.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class KatakanaTable {
    public static final HashMap<String, String> katakanaMap = new HashMap<String, String>();
    static {
        katakanaMap.put("ア" ,"あ");
        katakanaMap.put("イ" ,"い");
        katakanaMap.put("ウ" ,"う");
        katakanaMap.put("エ" ,"え");
        katakanaMap.put("オ" ,"お");
        katakanaMap.put("ー" ,"ー");
        katakanaMap.put("。" ,"。");
        katakanaMap.put("、" ,"、");

        katakanaMap.put("カ","か");
        katakanaMap.put("キ","き");
        katakanaMap.put("ク","く");
        katakanaMap.put("ケ","け");
        katakanaMap.put("コ","こ");

        katakanaMap.put("ガ","が");
        katakanaMap.put("ギ","ぎ");
        katakanaMap.put("グ","ぐ");
        katakanaMap.put("ゲ","げ");
        katakanaMap.put("ゴ","ご");

        katakanaMap.put("サ","さ");
        katakanaMap.put("シ","し");
        katakanaMap.put("ス","す");
        katakanaMap.put("セ","せ");
        katakanaMap.put("ソ","そ");

        katakanaMap.put("ザ","ざ");
        katakanaMap.put("ジ","じ");
        katakanaMap.put("ズ","ず");
        katakanaMap.put("ゼ","ぜ");
        katakanaMap.put("ゾ","ぞ");

        katakanaMap.put("タ","た");
        katakanaMap.put("チ","ち");
        katakanaMap.put("ツ","つ");
        katakanaMap.put("テ","て");
        katakanaMap.put("ト","と");

        katakanaMap.put("ッ","っ");

        katakanaMap.put("ダ","だ");
        katakanaMap.put("ヂ","ぢ");
        katakanaMap.put("ヅ","づ");
        katakanaMap.put("デ","で");
        katakanaMap.put("ド","ど");

        katakanaMap.put("ナ","な");
        katakanaMap.put("ニ","に");
        katakanaMap.put("ヌ","ぬ");
        katakanaMap.put("ネ","ね");
        katakanaMap.put("ノ","の");

        katakanaMap.put("ハ","は");
        katakanaMap.put("ヒ","ひ");
        katakanaMap.put("フ","ふ");
        katakanaMap.put("ヘ","へ");
        katakanaMap.put("ホ","ほ");

        katakanaMap.put("バ","ば");
        katakanaMap.put("ビ","び");
        katakanaMap.put("ブ","ぶ");
        katakanaMap.put("ベ","べ");
        katakanaMap.put("ボ","ぼ");

        katakanaMap.put("パ","ぱ");
        katakanaMap.put("ピ","ぴ");
        katakanaMap.put("プ","ぷ");
        katakanaMap.put("ペ","ぺ");
        katakanaMap.put("ポ","ぽ");

        katakanaMap.put("マ",  "ま");
        katakanaMap.put("ミ",  "み");
        katakanaMap.put("ム",  "む");
        katakanaMap.put("メ",  "め");
        katakanaMap.put("モ",  "も");

        katakanaMap.put("ヤ","や");
        katakanaMap.put("ユ","ゆ");
        katakanaMap.put("ヨ","よ");

        katakanaMap.put("ャ","ゃ");
        katakanaMap.put("ュ","ゅ");
        katakanaMap.put("ョ","ょ");

        katakanaMap.put("ラ","ら");
        katakanaMap.put("リ","り");
        katakanaMap.put("ル","る");
        katakanaMap.put("レ","れ");
        katakanaMap.put("ロ","ろ");

        katakanaMap.put("ワ","わ");
        katakanaMap.put("ヲ","を");

        katakanaMap.put("ン","ん");


    }

    public static final String katakanaRegex = "[あいうえおかきくけこがぎぐげごさしすせそざじずぜぞたちつてと" +
            "っだぢづでどなにぬねのはひふへほばびぶべぼぱぴぷぺぽまみむめもやゆよゃゅょらりるれろわをんー。、]";

    public static String toHiragana(String katakanaText) {
        if (katakanaText.matches("[ \t\n\f\r]*.*")) {
            katakanaText = katakanaText.replaceFirst("[ \t\n\f\r]*", "");
        }
        char[] text = katakanaText.toCharArray();
        ArrayList<String> syllabes = new ArrayList<String>();
        int i = 0;
        while (i <katakanaText.length()) {
            syllabes.add(Character.toString(text[i]));
            i++;
        }

        String converted = "";
        for (String syllabe : syllabes) {
            if (katakanaMap.containsKey(syllabe)) {
                converted = converted.concat(katakanaMap.get(syllabe));
            } else {
                converted = converted.concat(syllabe);
            }
        }

        return converted;
    }

}
