//package com.example.mighty.airtelapp;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.text.BreakIterator;
//import java.util.List;
//
//public abstract class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
//
//    class UserViewHolder extends RecyclerView.ViewHolder{
//        private final TextView userItemView;
//        public BreakIterator recipientNumber;
//        public BreakIterator dataBundleName;
//        public BreakIterator dataBundleValue;
//        public BreakIterator dataBundleCost;
//        public BreakIterator spinnerRow;
//
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            userItemView = itemView.findViewById(R.id.textView);
//        }
//    }
//
//    private final LayoutInflater mInflater;
//    List<User> users; //Cached copy of users
//
//    UserAdapter(Context context){
//        mInflater = LayoutInflater.from(context);
//    }
//
//    public UserAdapter(LayoutInflater mInflater, List<User> users){
//        this.mInflater = mInflater;
//        this.users = users;
//    }
//
//    public UserAdapter(MainActivity mainActivity, LayoutInflater mInflater) {
//        this.mInflater = mInflater;
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = mInflater.inflate(R.layout.recyclerview, parent, false);
//        return new UserViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        holder.recipientNumber.setText(users.get(position).getRecipientNumber());
//        holder.dataBundleName.setText(users.get(position).getDataBundleName());
//        holder.dataBundleValue.setText(users.get(position).getDataBundleValue());
//        holder.dataBundleCost.setText(users.get(position).getDataBundleCost());
//        holder.spinnerRow.setText(users.get(position).getSpinnerRow());
//    }
//
//    @Override
//    public int getItemCount() {
//        return users.size();
//    }
//
//
//    public class UserViewHolder extends RecyclerView.ViewHolder {
//        public TextView recipientNumber;
//        public TextView dataBundleName;
//        public TextView dataBundleValue;
//        public TextView dataBundleCost;
//        public TextView spinnerRow;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            recipientNumber = itemView.findViewById(R.id.recipient_number);
//            dataBundleName = itemView.findViewById(R.id.data_bundle_name);
//            dataBundleValue = itemView.findViewById(R.id.data_bundle_value);
//            dataBundleCost = itemView.findViewById(R.id.data_bundle_cost);
//            spinnerRow = itemView.findViewById(R.id.resource_spinner);
//        }
//    }
//}
