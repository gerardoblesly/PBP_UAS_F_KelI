package com.gerardoleonel.projectuts_eventorganizer.fragment.history;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.EventAPI;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Event;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Event> listEvent;
    private AdapterHistory adapter;
    View view;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_history, container, false);
        mAuth = FirebaseAuth.getInstance();
        setAdapter();
        getHistory();

        return view;
    }


    public void setAdapter(){
        getActivity().setTitle("Filography");
        listEvent = new ArrayList<Event>();
        recyclerView = view.findViewById(R.id.rvHistory);
        adapter = new AdapterHistory(listEvent, view.getContext(), new AdapterHistory.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete)
                {
                    getHistory();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getHistory() {
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Getting events....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, EventAPI.URL_SELECT_EVENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!listEvent.isEmpty())
                        listEvent.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        if(mAuth.getCurrentUser().getEmail().equalsIgnoreCase(jsonObject.optString("email")))
                        {

                            int id = jsonObject.optInt("id");
                            String eventType = jsonObject.optString("eventType");
                            String eventPlace = jsonObject.optString("eventPlace");
                            String eventPackage = jsonObject.optString("eventPackage");
                            String eventDescription = jsonObject.optString("eventDescription");

                            Event event = new Event(id, eventType, eventPlace, eventPackage, eventDescription);

                            listEvent.add(event);
                        }

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }



}