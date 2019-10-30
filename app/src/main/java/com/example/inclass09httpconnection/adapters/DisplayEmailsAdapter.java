package com.example.inclass09httpconnection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inclass09httpconnection.R;
import com.example.inclass09httpconnection.utils.EmailPO;

import java.util.ArrayList;


public class DisplayEmailsAdapter extends RecyclerView.Adapter<DisplayEmailsAdapter.ViewHolder> {

    ArrayList<EmailPO> my_emails = new ArrayList<>();
    AdapterInterface adapterInterface;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_email, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public DisplayEmailsAdapter(ArrayList<EmailPO> my_emails, AdapterInterface adapterInterface, Context context) {
        this.my_emails = my_emails;
        this.adapterInterface = adapterInterface;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.email_text.setText(my_emails.get(i).getMessage());
        viewHolder.ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterInterface.onDelete(i);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterInterface.onDisplay(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return my_emails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView email_text;
        ImageButton ib_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email_text = (TextView) itemView.findViewById(R.id.tv_emailtext);
            ib_delete = (ImageButton) itemView.findViewById(R.id.ib_delete);
        }
    }

    public interface AdapterInterface {
        public void onDelete(int index);

        public void onDisplay(int index);
    }

}
