package com.example.csc8019_teamprojectnotemplate.util;

/**
 * Public method classes for getting or setting data from firebase
 * @author: Ying Xie(Y.Xie28@newcastle.ac.uk)
 */
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

public class FireDatabase {

    public static final String TAG = "console";

    /**
     * setData() method: In order to set data to the firebase
     * @param tableName: The name of the root collection of the data structure
     * @param mapData: The set of HashMap's for the data you want to store
     * @author: Ying Xie(Y.Xie28@newcastle.ac.uk)
     */
    public static void setData(String tableName, Map<String, Object> mapData){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
//        Map<String, Object> mapData = new HashMap<>();
//        mapData.put("first", "Ada");
//        mapData.put("last", "Lovelace");
//        mapData.put("born", 1815);
        // Add a new document with a generated ID
        db.collection(tableName)
                .add(mapData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    /**
     * getOneTimeData() method: In order to retrieve data from the firebase
     * @param tableName: The name of the root collection of the data structure
     * @param documentName: The name of the document data in the data structure that you want to retrieve
     * @param fieldName: The name of the field data in the data structure that you want to retrieve
     * @author: Ying Xie(Y.Xie28@newcastle.ac.uk)
     */
    public static Object getOneTimeData(String tableName, String documentName, String fieldName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(tableName).document(documentName);
        Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
        while (true) {
            if (documentSnapshotTask.isSuccessful()) {
                //System.out.println(documentSnapshotTask.isSuccessful());
                break;
            }
        }
        DocumentSnapshot result = documentSnapshotTask.getResult();
        Map<String, Object> data = result.getData();
        if (data != null) {
            return data.get(fieldName);
        }
        return null;
    }

}
