package br.com.fatec.ecobit.Retrofit;
import java.util.List;
import java.util.Map;

import br.com.fatec.ecobit.model.Doacao;
import br.com.fatec.ecobit.model.Ponto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PontoAPI {

        @GET("/getAllPontos")
        Call<List<Ponto>> getPontos();

        @GET("/getPontoId/{id}")
        Call<Ponto> getPontoById(@Path("id") String id);
}

