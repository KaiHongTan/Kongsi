package com.kaihongtan.kongsi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {



    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView nameTextView;
        public TextView locationTextView;
        public Button messageButton;


        public ViewHolder(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById((R.id.imageView));
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            locationTextView = (TextView) itemView.findViewById(R.id.contact_location);
            messageButton = (Button) itemView.findViewById(R.id.message_button);

        }

    }
        private Context mCtx;
        public List<Contact> mContacts;

        // Pass in the contact array into the constructor
        public ContactsAdapter(Context mCtx, List<Contact> contacts) {
            this.mCtx = mCtx;
            mContacts = contacts;


    }



        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(this.mCtx);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.item_contact, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        // Involves populating data into the item through holder

        public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, final int position) {
            // Get the data model based on position
            final Contact contact = mContacts.get(position);

            Glide.with(mCtx).load(contact.getImage()).into(viewHolder.imageView);
            // Set item views based on your views and data model
            TextView textView = viewHolder.nameTextView;
            textView.setText(contact.getName());
            TextView locationView = viewHolder.locationTextView;
            locationView.setText(contact.getLocation());
            Button button = viewHolder.messageButton;
            button.setText("Pick Up");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("tag1", "ID is"+contact.getid());
                    deleteData del = new deleteData();
                    del.deleteData(contact.getid(), mCtx);
                    notifyDataSetChanged();

                }
            });

        }

        // Returns the total count of items in the list
        public int getItemCount() {
            return mContacts.size();

        }

        class deleteData{
            void deleteData(final int id, Context ctx){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://kaihongtan.com/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                        Log.i("tag2", ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                        // Showing error message if something goes wrong.
                        volleyError.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", String.valueOf(id));

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }}

}
