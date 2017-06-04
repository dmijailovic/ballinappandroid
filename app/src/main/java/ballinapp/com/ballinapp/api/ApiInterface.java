package ballinapp.com.ballinapp.api;

import java.util.List;

import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.data.NewRequest;
import ballinapp.com.ballinapp.data.Player;
import ballinapp.com.ballinapp.data.Request;
import ballinapp.com.ballinapp.data.Team;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/teams/check/{id}")
    Call<String> checkAccount(@Path("id") Long id);

    @POST("/teams")
    Call<Void> addTeam(@Body Team team);

    @GET("/teams/{teamId}")
    Call<Team> getTeamById(@Path("teamId") Long teamId);

    @GET("/teams/{teamId}/players")
    Call<List<Player>> getPlayersByTeam(@Path("teamId") Long teamId);

    @POST("/teams/{teamId}/players")
    Call<Void> addPlayer(@Path("teamId") Long teamId, @Body Player player);

    @DELETE("/teams/{teamId}/players/{playerId}")
    Call<Void> deletePlayer(@Path("teamId") Long teamId, @Path("playerId") int playerId);

    @GET("/team/{teamName}")
    Call<List<Team>> findTeamByName(@Path("teamName") String teamName);

    @GET("/cities/{cityName}")
    Call<List<Team>> findTeamsByCity(@Path("cityName") String cityName);

    @GET("/games/{cityName}")
    Call<List<Game>> findGamesByCity(@Path("cityName") String cityName);

    @POST("/games/{gameId}")
    Call<Void> joinGame(@Path("gameId") int gameId, @Body Team team);

    @GET("/game/{teamId}")
    Call<List<Game>> getMyGames(@Path("teamId") Long teamId);

    @HTTP(method = "DELETE", path = "/games/{gameId}", hasBody = true)
    Call<Void> leaveGame(@Path("gameId") int gameId, @Body Team team);

    @POST("/games")
    Call<Void> createGame(@Body Game game);

    @POST("/requests")
    Call<Void> sendRequest(@Body Request request);

    @GET("/requests/new/{teamId}")
    Call<List<NewRequest>> getRequests(@Path("teamId") Long teamId);

    @PUT("/requests/{requestId}/{response}")
    Call<Void> requestResponse(@Path("requestId") int requestId, @Path("response") boolean response);

    @PUT("/teams/{teamId}/appearance/{value}")
    Call<Void> updateAppearance(@Path("teamId") Long teamId, @Path("value") String value);

    @GET("/requests/sent/{teamId}")
    Call<List<NewRequest>> getSentRequests(@Path("teamId") Long teamId);

    @DELETE("/requests/{requestId}")
    Call<Void> deleteRequest(@Path("requestId") int requestId);

    @PUT("/requests/{requestId}")
    Call<Void> removeFromMyRequests(@Path("requestId") int requestId);

    @PUT("/teams/{teamId}/open")
    Call<Void> updateAvailability(@Path("teamId") Long teamId);
}
