package com.tangyx.game;


import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.tangyx.game.holder.GameView;

public class GameActivity extends BaseHomeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(getString(R.string.type), R.id.playo);
        int cos = getIntent().getIntExtra(getString(R.string.cos),0);
        String background = getIntent().getStringExtra(getString(R.string.cosback));
        GameView view = new GameView(this,type,background,cos);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setKeepScreenOn(true);
        setContentView(view);
    }
}
