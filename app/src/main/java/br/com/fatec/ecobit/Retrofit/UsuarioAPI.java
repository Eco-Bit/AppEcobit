package br.com.fatec.ecobit.Retrofit;
import br.com.fatec.ecobit.model.Usuario;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @POST("/save")
    Call<Usuario> save(@Body Usuario usuario);

    @POST("/login")
    Call<Map<String, Object>> login(@Body Usuario usuario);

    @GET("/getUserId/{id}")
    Call<Usuario> getUserById(@Path("id") String id);

    @PUT("/updateUser/{id}")
    Call<Void> updateUser(@Path("id") String id, @Body Usuario usuario);
}
