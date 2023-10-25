package com.rafac183.reciclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafac183.reciclerview.R;
import com.rafac183.reciclerview.SuperHero;
import com.rafac183.reciclerview.SuperHeroInterface;
import com.rafac183.reciclerview.databinding.CardViewBinding;

import java.util.ArrayList;
import java.util.function.Consumer;

public class SuperHeroAdapter extends RecyclerView.Adapter<SuperHeroViewHolder> {

    private final ArrayList<SuperHero> superHeroList;
    private final SuperHeroInterface clickCardView;

    public SuperHeroAdapter(ArrayList<SuperHero> superHeroList, SuperHeroInterface clickCardView) {
        this.superHeroList = superHeroList;
        this.clickCardView = clickCardView;
    }

    @NonNull
    @Override
    public SuperHeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new SuperHeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperHeroViewHolder holder, int position) {
        SuperHero item = superHeroList.get(position);
        holder.Render(item, clickCardView);
    }

    @Override
    public int getItemCount() {
        return superHeroList.size();
    }
}
