package com.kaihongtan.kongsi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaihongtan.kongsi.Contact;
import com.kaihongtan.kongsi.ContactsAdapter;
import com.kaihongtan.kongsi.NewListing;
import com.kaihongtan.kongsi.R;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    Button newlisting;
    RecyclerView recycler;
    List<Contact> contacts;
    Button refresh;
    private static final String URL_DATABASE = "http://kaihongtan.com/api.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recycler = root.findViewById(R.id.rvContacts);
        newlisting = root.findViewById(R.id.button2);
        newlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), NewListing.class);
                startActivity(i);
            }
        });
        contacts = new ArrayList<>();
        ContactsAdapter adapter = new ContactsAdapter(getActivity(),contacts);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        loadProducts();
        refresh = root.findViewById(R.id.button4);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.clear();
                loadProducts();
            }
        });
        return root;


    }
    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATABASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                contacts.add(new Contact(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("Location"),
                                        product.getString("image")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            recycler.getAdapter().notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}