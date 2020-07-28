package Entities;

import com.google.firebase.database.DataSnapshot;

public interface ConnectivityListener {
    void onConnected();
    void onDisconnected();
    void onFailure();
    void onStart();
}
