package com.rafac183.reciclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rafac183.reciclerview.adapter.SuperHeroAdapter;
import com.rafac183.reciclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SuperHeroInterface {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); //inflate
        setContentView(binding.getRoot());
        initRecyclerView();

    }

    public void initRecyclerView(){
        //LinearLayoutManager manager = new GridLayoutManager(this, 2); //Con esto puedo agregar un numero de filas especificas envez de 1
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, manager.getOrientation());
        binding.recyclerHero.setHasFixedSize(true); //Extra
        binding.recyclerHero.setItemAnimator(new DefaultItemAnimator());//Extra
        binding.recyclerHero.setLayoutManager(manager);
        binding.recyclerHero.setAdapter(new SuperHeroAdapter(MyData.getSuperHeroes(), this)); //tambien puede ser esto superHero -> onClickCardView(superHero) o esto this::onClickCardView
    }

    @Override
    public void onClickCardView(SuperHero superHero) {
        Toast.makeText(this, superHero.getPoder(), Toast.LENGTH_SHORT).show();
    }
}