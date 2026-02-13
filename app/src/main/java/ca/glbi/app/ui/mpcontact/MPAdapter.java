package ca.glbi.app.ui.mpcontact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.glbi.app.R;
import ca.glbi.app.model.MemberOfParliament;

public class MPAdapter extends RecyclerView.Adapter<MPAdapter.ViewHolder> {

    private List<MemberOfParliament> mps = new ArrayList<>();
    private final OnMPClickListener listener;

    public interface OnMPClickListener {
        void onEmailClick(MemberOfParliament mp);
    }

    public MPAdapter(OnMPClickListener listener) {
        this.listener = listener;
    }

    public void setMPs(List<MemberOfParliament> mps) {
        this.mps = mps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemberOfParliament mp = mps.get(position);
        holder.bind(mp, listener);
    }

    @Override
    public int getItemCount() {
        return mps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textMPName;
        private final TextView textRiding;
        private final TextView textProvince;
        private final TextView textParty;
        private final TextView textEmail;
        private final TextView textPhone;
        private final Button btnEmailMP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMPName = itemView.findViewById(R.id.text_mp_name);
            textRiding = itemView.findViewById(R.id.text_riding);
            textProvince = itemView.findViewById(R.id.text_province);
            textParty = itemView.findViewById(R.id.text_party);
            textEmail = itemView.findViewById(R.id.text_email);
            textPhone = itemView.findViewById(R.id.text_phone);
            btnEmailMP = itemView.findViewById(R.id.btn_email_mp);
        }

        public void bind(MemberOfParliament mp, OnMPClickListener listener) {
            textMPName.setText(mp.getName());
            textRiding.setText(mp.getRiding());
            textProvince.setText(mp.getProvince());
            textParty.setText(mp.getParty());
            textEmail.setText(mp.getEmail());
            textPhone.setText(mp.getPhone());
            
            btnEmailMP.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEmailClick(mp);
                }
            });
        }
    }
}
