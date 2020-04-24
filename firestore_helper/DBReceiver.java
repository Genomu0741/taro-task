package genomu.firestore_helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

public abstract class DBReceiver extends BroadcastReceiver {
    public final static String DES_STR = "string_fs_help";
    public final static String DES_LIST = "list_fs_help";
    public final static String DES_POJO = "pojo_fs_help";

    @Override
    public void onReceive(Context context, Intent intent) {
        String string = intent.getStringExtra(DES_STR);
        List list = intent.getParcelableArrayListExtra(DES_LIST);
        Serializable pojo = intent.getSerializableExtra(DES_POJO);
        onReceive(string);
        onReceive(list);
        onReceive(pojo);
    }

    public abstract void onReceive(String receivedString);
    public abstract void onReceive(List receivedList);
    public abstract void onReceive(Object receivedPOJO);
}
