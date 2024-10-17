package br.com.fatec.ecobit.Retrofit;

import java.util.List;

import br.com.fatec.ecobit.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioAPI {
@POST("/save")
    Call<Usuario> save(@Body Usuario usuario);
}
