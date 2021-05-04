package cz.hernik.kaku;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HiraganaWritingStartFragment extends Fragment {

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hiragana_writing_start, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wow isn't this pretty
                boolean a_row = ((CheckBox) view.findViewById(R.id.check_a_row)).isChecked();
                boolean k_row = ((CheckBox) view.findViewById(R.id.check_k_row)).isChecked();
                boolean n_row = ((CheckBox) view.findViewById(R.id.check_n_row)).isChecked();
                boolean s_row = ((CheckBox) view.findViewById(R.id.check_s_row)).isChecked();
                boolean t_row = ((CheckBox) view.findViewById(R.id.check_t_row)).isChecked();
                boolean h_row = ((CheckBox) view.findViewById(R.id.check_h_row)).isChecked();
                boolean m_row = ((CheckBox) view.findViewById(R.id.check_m_row)).isChecked();
                boolean y_row = ((CheckBox) view.findViewById(R.id.check_y_row)).isChecked();
                boolean r_row = ((CheckBox) view.findViewById(R.id.check_r_row)).isChecked();
                boolean w_row = ((CheckBox) view.findViewById(R.id.check_w_row)).isChecked();
                boolean g_row = ((CheckBox) view.findViewById(R.id.check_g_row)).isChecked();
                boolean z_row = ((CheckBox) view.findViewById(R.id.check_z_row)).isChecked();
                boolean d_row = ((CheckBox) view.findViewById(R.id.check_d_row)).isChecked();
                boolean b_row = ((CheckBox) view.findViewById(R.id.check_b_row)).isChecked();
                boolean p_row = ((CheckBox) view.findViewById(R.id.check_p_row)).isChecked();
                boolean n = ((CheckBox) view.findViewById(R.id.check_n)).isChecked();

                List<String> hiragana = new ArrayList<>();
                if(a_row){
                    List<String> a = Arrays.asList("あ","え","い","お","う");
                    hiragana.addAll(a);
                }
                if(k_row){
                    List<String> a = Arrays.asList("か","け","き","こ","く");
                    hiragana.addAll(a);
                }
                if(n_row){
                    List<String> a = Arrays.asList("な","ね","に","の","ぬ");
                    hiragana.addAll(a);
                }
                if(s_row){
                    List<String> a = Arrays.asList("さ","せ","し","そ","す");
                    hiragana.addAll(a);
                }
                if(t_row){
                    List<String> a = Arrays.asList("た","て","ち","と","つ");
                    hiragana.addAll(a);
                }
                if(h_row){
                    List<String> a = Arrays.asList("は","へ","ひ","ほ","ふ");
                    hiragana.addAll(a);
                }
                if(m_row){
                    List<String> a = Arrays.asList("ま","め","み","も","む");
                    hiragana.addAll(a);
                }
                if(y_row){
                    List<String> a = Arrays.asList("や","よ","ゆ");
                    hiragana.addAll(a);
                }
                if(r_row){
                    List<String> a = Arrays.asList("ら","れ","り","ろ","る");
                    hiragana.addAll(a);
                }
                if(w_row){
                    List<String> a = Arrays.asList("わ","を");
                    hiragana.addAll(a);
                }
                if(g_row){
                    List<String> a = Arrays.asList("が","げ","ぎ","ご","ぐ");
                    hiragana.addAll(a);
                }
                if(z_row){
                    List<String> a = Arrays.asList("ざ","ぜ","じ","ず","ぞ");
                    hiragana.addAll(a);
                }
                if(d_row){
                    List<String> a = Arrays.asList("だ","で","ぢ","ど","づ");
                    hiragana.addAll(a);
                }
                if(b_row){
                    List<String> a = Arrays.asList("ば","べ","び","ぼ","ぶ");
                    hiragana.addAll(a);
                }
                if(p_row){
                    List<String> a = Arrays.asList("ぱ","ぺ","ぴ","ぽ","ぷ");
                    hiragana.addAll(a);
                }
                if(n){
                    hiragana.add("ん");
                }

                startWriting(hiragana.toArray(new String[0]));
            }
        });
    }

    private void startWriting(String[] hiragana){
        Fragment f = null;
        try {
            f = HiraganaWritingFragment.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle args = new Bundle();
        args.putStringArray("hiraganalist",hiragana);
        f.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,f).commit();
    }
}