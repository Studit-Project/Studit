package com.example.studit.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studit.R;

import java.util.ArrayList;

public class FragSearchFreeAdapter extends RecyclerView.Adapter<FragSearchFreeAdapter.FragSearchFreeViewHolder> {

    private final ArrayList<MyStudyActivityModel> freeModelArrayList;
    private Context context;
    String getContentsNum;
    int pos;

    public FragSearchFreeAdapter(ArrayList<MyStudyActivityModel> freeModelArrayList, Context context) {
        this.freeModelArrayList = freeModelArrayList;
        this.context = context;
    }

    public FragSearchFreeAdapter(ArrayList<MyStudyActivityModel> freeModelArrayList) {
        this.freeModelArrayList = freeModelArrayList;
    }

    public class FragSearchFreeViewHolder extends RecyclerView.ViewHolder {
        public TextView state, title, tag, progress;

        public FragSearchFreeViewHolder(View view) {
            super(view);
            this.title = view.findViewById(R.id.list_search_free_title);
            this.state = view.findViewById(R.id.list_search_free_state);
            this.tag = view.findViewById(R.id.list_search_free_tag);
            this.progress = view.findViewById(R.id.list_search_free_progress);

            view.setClickable(true);
            view.setOnClickListener(v -> {
                pos = getAdapterPosition();

                if (pos != RecyclerView.NO_POSITION) {

                    Log.d("pos", pos + " 클릭됨");
//                    FragHomeStudyModel item = StudyModelArrayList.get(pos);
//
//                    getContentsNum = item.getContentsNum();
//                    String getTitle = item.getTitle();
//                    String getDay = item.getDay();
//                    String getInfo = item.getInfo();
//
//                    Intent intent = new Intent(context, FragHome.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("getContentsNum", getContentsNum);
//                    intent.putExtra("getTitle", getTitle);
//                    intent.putExtra("getDay", getDay);
//                    intent.putExtra("getInfo", getInfo);
//
//                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public FragSearchFreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_frag_search_free, parent, false);
        FragSearchFreeViewHolder holder = new FragSearchFreeViewHolder(cardView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FragSearchFreeViewHolder holder, int position) {

        MyStudyActivityModel dataModelPosition = freeModelArrayList.get(position);
        holder.title.setText(dataModelPosition.getTitle());
        holder.state.setText(dataModelPosition.getState());
        holder.tag.setText(dataModelPosition.getTag());
        holder.progress.setText(dataModelPosition.getProgress());

        context = holder.itemView.getContext();

        /* 리사이클러뷰의 버튼을 클릭할 때 실행될 것들을 적어준다. */
        //holder.day.setOnClickListener(v -> Toast.makeText(context, "리사이클러뷰의 날짜가 클릭되었습니다.", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return (freeModelArrayList != null ? freeModelArrayList.size() : 0);
    }
}
