package cz.hernik.kaku.helper;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;

public class HiraganaEditText extends androidx.appcompat.widget.AppCompatEditText {
    /*
     * Created by jehutyno
     */
    private boolean enableConversion = true;

    public HiraganaEditText(Context context) {
        super(context);
        init();
    }

    public HiraganaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HiraganaEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(hiraganaTextWatcher);
    }

    private TextWatcher hiraganaTextWatcher = new TextWatcher() {
        public int position;
        public int sizeBefore;
        private boolean flagBeforeTextChange;
        private boolean flagAfterTextChange = false;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!flagBeforeTextChange && enableConversion) {
                flagBeforeTextChange = true;
                sizeBefore = s.length();
                position = start;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!flagAfterTextChange && enableConversion && s.length() >= sizeBefore) {
                flagAfterTextChange = true;
                String japText = toHiragana(s.toString());
                if (!s.toString().equals(japText)) {
                    setText(japText);
                    if (japText.length() > 0) {
                        int selection = position + japText.length() - sizeBefore + (japText.length() < sizeBefore ? 1 : 0);
                        if (selection > getText().length())
                            selection = getText().length();
                        setSelection(selection);
                    }
                }
            }
            flagAfterTextChange = false;
            flagBeforeTextChange = false;
        }

        public String toHiragana(String romajiText) {
            if (romajiText.matches("[ \t\n\f\r]*.*")) {
                romajiText = romajiText.replaceFirst("[ \t\n\f\r]*", "");
            }
            String remainingText = romajiText;
            ArrayList<String> syllabes = new ArrayList<String>();

            while (remainingText.length() > 0) {

                //vowel alone or hiragana or . or -
                if (remainingText.substring(0, 1).matches("[aeuio-[.]]") || remainingText.length() == 1
                        || remainingText.substring(0, 1).matches(HiraganaTable.hiraganaRegex)) {
                    syllabes.add(remainingText.substring(0, 1));
                    if (remainingText.length() > 1) {
                        remainingText = remainingText.substring(1, remainingText.length());
                    } else {
                        remainingText = "";
                    }
                    //n followed by not a vowel
                } else if (remainingText.substring(0, 1).matches("n") && !remainingText.substring(1, 2).matches("[aeyuion-[.]]")) {
                    syllabes.add("nn");
                    remainingText = remainingText.substring(1, remainingText.length());
                    //two letters syllabes
                } else if (remainingText.substring(1, 2).matches("[aeuio]")
                        || remainingText.substring(0, 2).matches("nn")
                        || remainingText.length() == 2) {
                    syllabes.add(remainingText.substring(0, 2));
                    if (remainingText.length() > 2) {
                        remainingText = remainingText.substring(2, remainingText.length());
                    } else {
                        remainingText = "";
                    }
                    //three letters syllabes
                } else if (remainingText.substring(2, 3).matches("[aeuio]") || remainingText.length() >= 3) {
                    //little tsu case with two following
                    if (remainingText.substring(0, 1).equals(remainingText.substring(1, 2))) {
                        syllabes.add("lttu");
                        remainingText = remainingText.substring(1, remainingText.length());

                        //normal 3 letters syllabes case
                    } else {
                        syllabes.add(remainingText.substring(0, 3));
                        remainingText = remainingText.substring(3, remainingText.length());
                    }
                }

            }

            String hiraganaText = "";
            for (String syllabe : syllabes) {
                if (HiraganaTable.hiraganaMap.containsKey(syllabe)) {
                    hiraganaText = hiraganaText.concat(HiraganaTable.hiraganaMap
                            .get(syllabe));
                } else {
                    hiraganaText = hiraganaText.concat(syllabe);
                }
            }

            return hiraganaText;
        }
    };

    public boolean isEnableConversion() {
        return enableConversion;
    }

    public void setEnableConversion(boolean enableConversion) {
        this.enableConversion = enableConversion;
    }
}
