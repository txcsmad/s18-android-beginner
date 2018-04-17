package ahn.spring2018;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class KittensFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kittens, container, false);
        setupRecyclerView(rootView);
        return rootView;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.kitten_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        KittenAdapter adapter = new KittenAdapter(10000);
        recyclerView.setAdapter(adapter);
    }

    private static class KittenAdapter extends RecyclerView.Adapter<KittenHolder> {

        private int size;

        public KittenAdapter(int size) {
            this.size = size;
        }

        @Override
        public KittenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View kittenView = layoutInflater.inflate(R.layout.kitten_item, null);
            return new KittenHolder(kittenView);
        }

        @Override
        public void onBindViewHolder(KittenHolder holder, int position) {
            String url1 = "http://thecatapi.com/api/images/get?format=src&type=png";
            String url2 = "http://theoldreader.com/kittens/1200/720";

            String kittenUrl = position % 2 == 0 ? url1 : url2;

            Context context = holder.itemView.getContext();

            Picasso.with(context).load(kittenUrl).into(holder.kittenImageView);
        }

        @Override
        public int getItemCount() {
            return size;
        }
    }

    private static class KittenHolder extends RecyclerView.ViewHolder {

        ImageView kittenImageView;

        public KittenHolder(View itemView) {
            super(itemView);

            kittenImageView = itemView.findViewById(R.id.kitten_image);
        }
    }
}
