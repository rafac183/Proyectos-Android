package com.rafac183.reciclerview.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rafac183.reciclerview.SuperHero;
import com.rafac183.reciclerview.SuperHeroInterface;
import com.rafac183.reciclerview.databinding.CardViewBinding;

public class SuperHeroViewHolder extends RecyclerView.ViewHolder {

    private CardViewBinding binding;

    public SuperHeroViewHolder(View view) {
        super(view);
        binding = CardViewBinding.bind(view);
    }

    public void Render(SuperHero superHeroModel, SuperHeroInterface clickCardView) {
        binding.superHeroName.setText(superHeroModel.getName());
        binding.publisher.setText(superHeroModel.getPublisher());
        binding.power.setText(superHeroModel.getPoder());
        Glide.with(binding.imageView.getContext()).load(superHeroModel.getImage()).into(binding.imageView);

        binding.cardView.setOnClickListener(v -> clickCardView.onClickCardView(superHeroModel));

        /*binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(binding.imageView.getContext(), superHeroModel.getPoder(), Toast.LENGTH_SHORT).show();
            }
        });*/

        /*itemView.setOnClickListener(new View.OnClickListener() { //Este Selecciona Literalmente todo el layout
            @Override
            public void onClick(View v) {
                Toast.makeText(binding.imageView.getContext(), superHeroModel.getPoder(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}