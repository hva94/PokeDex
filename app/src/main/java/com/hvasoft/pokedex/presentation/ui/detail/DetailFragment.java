package com.hvasoft.pokedex.presentation.ui.detail;

import static com.hvasoft.pokedex.presentation.ui.common.UIExtensionFunctions.loadImageWithUrl;
import static com.hvasoft.pokedex.presentation.ui.common.UIExtensionFunctions.showPopUpMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hvasoft.domain.model.Pokemon;
import com.hvasoft.domain.model.PokemonType;
import com.hvasoft.pokedex.R;
import com.hvasoft.pokedex.databinding.FragmentDetailBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel detailViewModel;
    private Pokemon pokemon;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupViewModel() {
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.detailState.observe(getViewLifecycleOwner(), detailState -> {
            if (detailState instanceof DetailState.Success successState) {
                binding.detailProgressBar.setVisibility(View.GONE);
                pokemon = successState.getPokemon();
                binding.btnFavorite.setText(pokemon.isFavorite() ? getString(R.string.remove_favorite) : getString(R.string.add_favorite));
            } else if (detailState instanceof DetailState.Failure) {
                showPopUpMessage(binding.getRoot(), ((DetailState.Failure) detailState).getMessage(), true);
                binding.detailProgressBar.setVisibility(View.GONE);
            } else {
                binding.detailProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupView() {
        pokemon = getPokemonByArgs();
        binding.tvName.setText(pokemon.getName());
        loadImageWithUrl(binding.ivPhoto, pokemon.getSprites().get(0).getFrontDefault());
        binding.tvHeight.setText(getString(R.string.label_height, pokemon.getHeight()));
        binding.tvWeight.setText(getString(R.string.label_weight, pokemon.getWeight()));
        bindTypes(pokemon.getTypes());
        binding.btnFavorite.setText(pokemon.isFavorite() ? getString(R.string.remove_favorite) : getString(R.string.add_favorite));
        binding.btnFavorite.setOnClickListener(v -> detailViewModel.toggleFavoritePokemon(pokemon));
    }

    private Pokemon getPokemonByArgs() {
        Bundle args = getArguments();
        if (args != null) {
            Pokemon pokemon = args.getParcelable("pokemon");
            if (pokemon != null)
                return pokemon;
        }
        throw new IllegalStateException(getString(R.string.error_unknown));
    }

    private void bindTypes(List<PokemonType> types) {
        StringBuilder typesString = new StringBuilder();
        for (PokemonType type : types) {
            typesString.append(type.getName()).append(", ");
        }
        typesString.delete(typesString.length() - 2, typesString.length());
        binding.tvTypes.setText(getString(R.string.label_types, typesString.toString()));
    }

}