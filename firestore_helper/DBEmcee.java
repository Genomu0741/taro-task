package genomu.firestore_helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public interface DBEmcee {
    void goLive(Query query);
    void goLive(Task<DocumentSnapshot> snapshotTask);
}
