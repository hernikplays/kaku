package cz.hernik.kaku.helper;

import java.util.HashMap;

public class HiraganaTable {
    public static final HashMap<String, String> hiraganaMap = new HashMap<String, String>();
    static {
        /*
          Created by jehutyno
         */

        //-
        hiraganaMap.put("a", "あ");
        hiraganaMap.put("i", "い");
        hiraganaMap.put("u", "う");
        hiraganaMap.put("e", "え");
        hiraganaMap.put("o", "お");
        hiraganaMap.put("-", "ー");
        hiraganaMap.put(".", "。");
        hiraganaMap.put(",","、");
        hiraganaMap.put("?","？");
        hiraganaMap.put("!","！");

        //K
        hiraganaMap.put("ka", "か");
        hiraganaMap.put("ki", "き");
        hiraganaMap.put("ku", "く");
        hiraganaMap.put("ke", "け");
        hiraganaMap.put("ko", "こ");

        //G
        hiraganaMap.put("ga", "が");
        hiraganaMap.put("gi", "ぎ");
        hiraganaMap.put("gu", "ぐ");
        hiraganaMap.put("ge", "げ");
        hiraganaMap.put("go", "ご");

        //S
        hiraganaMap.put("sa", "さ");
        hiraganaMap.put("si", "し");
        hiraganaMap.put("shi", "し");
        hiraganaMap.put("su", "す");
        hiraganaMap.put("se", "せ");
        hiraganaMap.put("so", "そ");

        //Z
        hiraganaMap.put("za", "ざ");
        hiraganaMap.put("ji", "じ");
        hiraganaMap.put("zu", "ず");
        hiraganaMap.put("ze", "ぜ");
        hiraganaMap.put("zo", "ぞ");

        //T
        hiraganaMap.put("ta", "た");
        hiraganaMap.put("ti", "ち");
        hiraganaMap.put("chi", "ち");
        hiraganaMap.put("tu", "つ");
        hiraganaMap.put("tsu", "つ");
        hiraganaMap.put("te", "て");
        hiraganaMap.put("to", "と");

        hiraganaMap.put("lttu", "っ");
        hiraganaMap.put("lli", "ぃ");
        hiraganaMap.put("llu", "ぅ");
        hiraganaMap.put("lla", "ぁ");
        hiraganaMap.put("lle", "ぇ");
        hiraganaMap.put("llo", "ぉ");

        //D
        hiraganaMap.put("da", "だ");
        hiraganaMap.put("di", "ぢ");
        hiraganaMap.put("du", "づ");
        hiraganaMap.put("de", "で");
        hiraganaMap.put("do", "ど");

        //N
        hiraganaMap.put("na", "な");
        hiraganaMap.put("ni", "に");
        hiraganaMap.put("nu", "ぬ");
        hiraganaMap.put("ne", "ね");
        hiraganaMap.put("no", "の");

        //H
        hiraganaMap.put("ha", "は");
        hiraganaMap.put("hi", "ひ");
        hiraganaMap.put("hu", "ふ");
        hiraganaMap.put("fu", "ふ");
        hiraganaMap.put("he", "へ");
        hiraganaMap.put("ho", "ほ");

        //B
        hiraganaMap.put("ba", "ば");
        hiraganaMap.put("bi", "び");
        hiraganaMap.put("bu", "ぶ");
        hiraganaMap.put("be", "べ");
        hiraganaMap.put("bo",  "ぼ");

        //P
        hiraganaMap.put("pa",  "ぱ");
        hiraganaMap.put("pi",  "ぴ");
        hiraganaMap.put("pu",  "ぷ");
        hiraganaMap.put("pe",  "ぺ");
        hiraganaMap.put("po",  "ぽ");

        //M
        hiraganaMap.put("ma",  "ま");
        hiraganaMap.put("mi",  "み");
        hiraganaMap.put("mu",  "む");
        hiraganaMap.put("me",  "め");
        hiraganaMap.put("mo",  "も");

        //Y
        hiraganaMap.put("ya",  "や");
        hiraganaMap.put("yu",  "ゆ");
        hiraganaMap.put("yo",  "よ");

        hiraganaMap.put("ltya",  "ゃ");
        hiraganaMap.put("ltyu",  "ゅ");
        hiraganaMap.put("ltyo",  "ょ");

        //R
        hiraganaMap.put("ra",  "ら");
        hiraganaMap.put("la",  "ら");
        hiraganaMap.put("ri",  "り");
        hiraganaMap.put("li",  "り");
        hiraganaMap.put("ru",  "る");
        hiraganaMap.put("lu",  "る");
        hiraganaMap.put("re",  "れ");
        hiraganaMap.put("le",  "れ");
        hiraganaMap.put("ro",  "ろ");
        hiraganaMap.put("lo",  "ろ");

        //W
        hiraganaMap.put("wa",  "わ");
//		hiraganaMap.put("ltwa",  "");
        hiraganaMap.put("wo",  "を");

        //nn
//		hiraganaMap.put("n",  "ん");
        hiraganaMap.put("nn",  "ん");

        //Ky
        hiraganaMap.put("kya",  "きゃ");
        hiraganaMap.put("kyu",  "きゅ");
        hiraganaMap.put("kyo",  "きょ");

        //Gy
        hiraganaMap.put("gya",  "ぎゃ");
        hiraganaMap.put("gyu",  "ぎゅ");
        hiraganaMap.put("gyo",  "ぎょ");

        //S
        hiraganaMap.put("sha",  "しゃ");
        hiraganaMap.put("shu",  "しゅ");
        hiraganaMap.put("sho",  "しょ");

        //Jy
        hiraganaMap.put("ja",  "じゃ");
        hiraganaMap.put("jya",  "じゃ");
        hiraganaMap.put("ju",  "じゅ");
        hiraganaMap.put("jyu",  "じゅ");
        hiraganaMap.put("jo",  "じょ");
        hiraganaMap.put("jyo",  "じょ");

        //chy
        hiraganaMap.put("cha",  "ちゃ");
        hiraganaMap.put("chu",  "ちゅ");
        hiraganaMap.put("cho",  "ちょ");

        //ny
        hiraganaMap.put("nya",  "にゃ");
        hiraganaMap.put("nyu",  "にゅ");
        hiraganaMap.put("nyo",  "にょ");

        //hy
        hiraganaMap.put("hya",  "ひゃ");
        hiraganaMap.put("hyu",  "ひゅ");
        hiraganaMap.put("hyo",  "ひょ");

        //by
        hiraganaMap.put("bya",  "びゃ");
        hiraganaMap.put("byu",  "びゅ");
        hiraganaMap.put("byo",  "びょ");

        //p
        hiraganaMap.put("pya",  "ぴゃ");
        hiraganaMap.put("pyu",  "ぴゅ");
        hiraganaMap.put("pyo",  "ぴょ");

        //my
        hiraganaMap.put("mya",  "みゃ");
        hiraganaMap.put("myu",  "みゅ");
        hiraganaMap.put("myo",  "みょ");

        //ry
        hiraganaMap.put("rya",  "りゃ");
        hiraganaMap.put("ryu",  "りゅ");
        hiraganaMap.put("ryo",  "りょ");

        //ly
        hiraganaMap.put("lya",  "りゃ");
        hiraganaMap.put("lyu",  "りゅ");
        hiraganaMap.put("lyo",  "りょ");
    }

    public static final String hiraganaRegex = "[あいうえおかきくけこがぎぐげごさしすせそざじずぜぞたちつてと" +
            "っだぢづでどなにぬねのはひふへほばびぶべぼぱぴぷぺぽまみむめもやゆよゃゅょらりるれろわをんー。、？]";

    public static final HashMap<String, String> toRomajiMap = new HashMap<String, String>();
    static {
        //-
        toRomajiMap.put("あ","a");
        toRomajiMap.put("い","i");
        toRomajiMap.put("う","u");
        toRomajiMap.put("え","e");
        toRomajiMap.put("お","o");

        //K
        toRomajiMap.put("か","ka");
        toRomajiMap.put("き","ki");
        toRomajiMap.put("く","ku");
        toRomajiMap.put("け","ke");
        toRomajiMap.put("こ","ko");

        //G
        toRomajiMap.put("が","ga");
        toRomajiMap.put("ぎ","gi");
        toRomajiMap.put("ぐ","gu");
        toRomajiMap.put("げ","ge");
        toRomajiMap.put("ご","go");

        //S
        toRomajiMap.put("さ","sa");
        //toRomajiMap.put("し","si");
        toRomajiMap.put("し","shi");
        toRomajiMap.put("す","su");
        toRomajiMap.put("せ","se");
        toRomajiMap.put("そ","so");

        //Z
        toRomajiMap.put("ざ","za");
        toRomajiMap.put("じ","ji");
        toRomajiMap.put("ず","zu");
        toRomajiMap.put("ぜ","ze");
        toRomajiMap.put("ぞ","zo");

        //T
        toRomajiMap.put("た","ta");
        //toRomajiMap.put("ち","ti");
        toRomajiMap.put("ち","chi");
        //toRomajiMap.put("つ","tu");
        toRomajiMap.put("つ","tsu");
        toRomajiMap.put("て","te");
        toRomajiMap.put("と","to");

        //D
        toRomajiMap.put("だ","da");
        toRomajiMap.put("ぢ","di");
        toRomajiMap.put("づ","du");
        toRomajiMap.put("で","de");
        toRomajiMap.put("ど","do");

        //N
        toRomajiMap.put("な","na");
        toRomajiMap.put("に","ni");
        toRomajiMap.put("ぬ","nu");
        toRomajiMap.put("ね","ne");
        toRomajiMap.put("の","no");

        //H
        toRomajiMap.put("は","ha");
        toRomajiMap.put("ひ","hi");
        //toRomajiMap.put("ふ","hu");
        toRomajiMap.put("ふ","fu");
        toRomajiMap.put("へ","he");
        toRomajiMap.put("ほ","ho");

        //B
        toRomajiMap.put("ば","ba");
        toRomajiMap.put("び","bi");
        toRomajiMap.put("ぶ","bu");
        toRomajiMap.put("べ","be");
        toRomajiMap.put("ぼ","bo");

        //P
        toRomajiMap.put("ぱ","pa");
        toRomajiMap.put("ぴ","pi");
        toRomajiMap.put("ぷ","pu");
        toRomajiMap.put("ぺ","pe");
        toRomajiMap.put("ぽ","po");

        //M
        toRomajiMap.put("ま","ma");
        toRomajiMap.put("み","mi");
        toRomajiMap.put("む","mu");
        toRomajiMap.put("め","me");
        toRomajiMap.put("も","mo");

        //Y
        toRomajiMap.put("や","ya");
        toRomajiMap.put("ゆ","yu");
        toRomajiMap.put("よ","yo");

        //R
        toRomajiMap.put("ら","ra");
        toRomajiMap.put("り","ri");
        toRomajiMap.put("る","ru");
        toRomajiMap.put("れ","re");
        toRomajiMap.put("ろ","ro");

        //W
        toRomajiMap.put("わ","wa");
        toRomajiMap.put("を","wo");

        //n
        toRomajiMap.put("ん","n");
    }
}
