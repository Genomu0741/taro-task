package genomu.firestore_helper;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class HanWen {
    private static FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference reference;

    public HanWen(String key) {
        this.reference = database.collection(key);
    }

    public void sprout(String field,Object value){
        Map<String ,Object> map = new HashMap<>();
        map.put(field,value);
        reference.add(map);
    }

    public void sprout(Object value){
        reference.add(value);
    }

    public Query seek(String field, Object value){
        return reference.whereEqualTo(field,value);
    }
}
