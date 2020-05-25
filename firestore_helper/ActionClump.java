package genomu.firestore_helper;

import java.util.HashMap;
import java.util.Map;

public class ActionClump {
    private static Map<Class,String> ACTIONS = new HashMap<>();
    public static String putAction(Class E){
        String str =  ACTIONS.get(E);
        String className = E.getSimpleName() + ".ACTION";
        ACTIONS.put(E,className);
        return ACTIONS.get(E);
    }
    public static String getAction(Class E){
        return putAction(E);
    }
}
