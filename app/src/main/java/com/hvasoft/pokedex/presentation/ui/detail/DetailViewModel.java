package com.hvasoft.pokedex.presentation.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hvasoft.domain.interactor.ToggleFavoritePokemonUseCase;
import com.hvasoft.domain.model.Pokemon;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.rx3.RxSingleKt;

@HiltViewModel
public class DetailViewModel extends ViewModel {

    private final ToggleFavoritePokemonUseCase toggleFavoritePokemonUseCase;

    private final MutableLiveData<DetailState> _detailState = new MutableLiveData<>();
    public final LiveData<DetailState> detailState = _detailState;

    @Inject
    public DetailViewModel(
            ToggleFavoritePokemonUseCase toggleFavoritePokemonUseCase
    ) {
        this.toggleFavoritePokemonUseCase = toggleFavoritePokemonUseCase;
    }

    public void toggleFavoritePokemon(Pokemon pokemon) {
        _detailState.setValue(DetailState.Loading);
        try {
            Single<Pokemon> suspendResult = RxSingleKt.rxSingle(
                    EmptyCoroutineContext.INSTANCE,
                    (scope, continuation) -> toggleFavoritePokemonUseCase.invoke(pokemon, new Continuation<>() {
                        @NonNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NonNull Object result) {
                            continuation.resumeWith((Pokemon) result);
                        }
                    })
            );
            _detailState.setValue(new DetailState.Success(suspendResult.blockingGet()));
        } catch (Exception e) {
            _detailState.setValue(new DetailState.Failure(e.getMessage()));
        }
    }

}