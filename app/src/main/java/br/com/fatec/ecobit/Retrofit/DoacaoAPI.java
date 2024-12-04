package br.com.fatec.ecobit.Retrofit;

import java.util.List;

import br.com.fatec.ecobit.model.Doacao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DoacaoAPI {

    @GET("/getAllDoa") // Endpoint para listar doações
    Call<List<Doacao>> getDoacoes();

    @POST("/saveDoa") // Adicionado o endpoint correto
    Call<Doacao> salvarDoacao(@Body Doacao doacao);

    @GET("/getDoaId/{id}")
    Call<Doacao> getDoacaoById(@Path("id") String id);

    @GET("/getDoaUser/{id}")
    Call<List<Doacao>> getDoaUser(@Path("id")String id);

    @DELETE("/deleteDoa/{id}")
    Call<Void> deleteDoa(@Path("id")String id);

    @PUT("/updateDoa/{id}")
    Call<Void> updateDoacao(@Path("id") String id, @Body Doacao doacao);
}
