package me.jimmyshaw.retrofitstarter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

// The main idea behind Retrofit is that it's possible to generate code to query HTTP
// services at runtime, requiring the developer to produce just an interface as the
// specification. Contributor is our model class. This GitHubService interface will embody
// our HTTP communication.
public interface GitHubService {

    // We add the @GET annotation on an interface method and provide the path part of the URL that
    // we want to expose it on. The method parameters can be referenced in the path string so we
    // don't have to jump through hoops to set those.
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    // We build the retrofit object. A default converter factory is added to turn JSON response
    // objects into Java objects.
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
