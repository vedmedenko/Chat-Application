package com.vedmedenko.chat.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vedmedenko.chat.R;
import com.vedmedenko.chat.core.Message;
import com.vedmedenko.chat.ui.activities.base.BaseActivity;
import com.vedmedenko.chat.ui.activities.settings.SettingsActivity;
import com.vedmedenko.chat.ui.adapters.MessageAdapter;
import com.vedmedenko.chat.utils.ConstantsManager;
import com.vedmedenko.chat.utils.NotificationUtils;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    // UI

    @BindView(R.id.send_button)
    ImageButton buttonSend;

    @BindView(R.id.text)
    EditText editText;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    // Injection

    @Inject
    DatabaseReference databaseReference;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    MessageAdapter messageAdapter;

    private String nickname;
    private ChildEventListener childEventListener;
    private Set<String> ids;

    private boolean notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        notify = false;
        ids = new HashSet<>();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (notify) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (!ids.contains(dataSnapshot.getKey()))
                        NotificationUtils.notifyMessage(MainActivity.this, message);
                } else {
                    ids.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.child(ConstantsManager.COLLECTION_NAME).addChildEventListener(childEventListener);

        buttonSend.setOnClickListener((view -> {
            if (!editText.getText().toString().isEmpty()) {
                databaseReference.child(ConstantsManager.COLLECTION_NAME).push()
                        .setValue(new Message(nickname, editText.getText().toString().trim()));
                editText.setText("");
            }
        }));

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nickname = sharedPreferences.getString(ConstantsManager.PREFERENCE_NICNNAME, getString(R.string.pref_default_nickname));
        notify = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        notify = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageAdapter.cleanup();

        if (childEventListener != null) {
            databaseReference.child(ConstantsManager.COLLECTION_NAME).removeEventListener(childEventListener);
            childEventListener = null;
        }

        if (ids != null) {
            ids.clear();
            ids = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
    }
}
