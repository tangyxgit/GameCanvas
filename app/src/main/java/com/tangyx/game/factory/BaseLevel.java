package com.tangyx.game.factory;

import android.content.Context;
import android.util.SparseArray;

import java.util.Map;

/**
 * Created by tangyx on 2016/12/28.
 *
 */

public abstract class BaseLevel {
    private Context context;

    public BaseLevel(Context context,Object... objects) {
        this.context = context;
        initialize(objects);
    }

    public abstract void initialize(Object... objects);
    public abstract Map<Integer,Integer> enemyLevelArray(int enemyArrayIndex);

    public Context getContext() {
        return context;
    }
}
