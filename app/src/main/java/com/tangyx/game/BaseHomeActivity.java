package com.tangyx.game;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tangyx on 2016/12/19.
 *
 */

public class BaseHomeActivity extends AppCompatActivity {
    /**
     */
    public void FontManager(ViewGroup root, Activity act){
        Typeface tf = getFontFace(R.string.interstellar);
        if(root!= null){
            for (int i = 0; i < root.getChildCount(); i++) {
                View v = root.getChildAt(i);
                if(v instanceof TextView){
                    ((TextView) v).setTypeface(tf);
                }else if(v instanceof Button){
                    ((Button) v).setTypeface(tf);
                }else if(v instanceof ViewGroup){
                    FontManager(((ViewGroup)v),this);
                }
            }
        }
    }
    /**
     */
    public ViewGroup getAllView(Activity act){
        ViewGroup root=null;
        try {
            ViewGroup temp = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);
            if(temp.getChildCount()>0&&temp.getChildAt(0) instanceof ViewGroup){
                root = (ViewGroup) temp.getChildAt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
    /**
     */
    public Typeface getFontFace(int fontid){
        return Typeface.createFromAsset(this.getAssets(),getString(fontid));
    }
}
