package ballinapp.com.ballinapp.api;

import java.util.List;

import ballinapp.com.ballinapp.data.Game;
import ballinapp.com.ballinapp.data.NewRequest;
import ballinapp.com.ballinapp.data.Player;
import ballinapp.com.ballinapp.data.Request;
import ballinapp.com.ballinapp.data.Team;
import ballinapp.com.ballinapp.data.util.AppearanceUpdateBean;
import ballinapp.com.ballinapp.data.util.AppearanceUpdateEnum;
import ballinapp.com.ballinapp.data.util.JWTInfo;
import ballinapp.com.ballinapp.data.util.LoginInfo;
import ballinapp.com.ballinapp.data.util.RefreshInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("/register/login")
    Call<JWTInfo> login(@Body LoginInfo info);

    @POST("/register/check")
    Call<Boolean> checkIfLoggedIn(@Body Team team);

    @POST("/register/team")
    Call<JWTInfo> addTeam(@Body Team team);

    @GET("/teams/{teamId}")
    Call<Team> getTeamById(@Path("teamId") int teamId,
                           @Header("Authorization") String token);

    @GET("/teams/{teamId}/players")
    Call<List<Player>> getPlayersByTeam(@Path("teamId") int teamId,
                                        @Header("Authorization") String token);

    @POST("/teams/{teamId}/players")
    Call<Void> addPlayer(@Path("teamId") int teamId,
                         @Body Player player,
                         @Header("Authorization") String token);

    @DELETE("/teams/{teamId}/players/{playerId}")
    Call<Void> deletePlayer(@Path("teamId") int teamId,
                            @Path("playerId") int playerId,
                            @Header("Authorization") String token);

    @GET("/team/{teamName}")
    Call<List<Team>> findTeamByName(@Path("teamName") String teamName,
                                    @Header("Authorization") String token);

    @GET("/cities/{cityName}")
    Call<List<Team>> findTeamsByCity(@Path("cityName") String cityName,
                                     @Header("Authorization") String token);

    @GET("/games/{cityName}")
    Call<List<Game>> findGamesByCity(@Path("cityName") String cityName,
                                     @Header("Authorization") String token);

    @POST("/games/{gameId}")
    Call<Void> joinGame(@Path("gameId") int gameId,
                        @Body Team team,
                        @Header("Authorization") String token);

    @GET("/game/{teamId}")
    Call<List<Game>> getMyGames(@Path("teamId") int teamId,
                                @Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "/games/{gameId}", hasBody = true)
    Call<Void> leaveGame(@Path("gameId") int gameId,
                         @Body Team team,
                         @Header("Authorization") String token);

    @POST("/games")
    Call<Void> createGame(@Body Game game,
                          @Header("Authorization") String token);

    @POST("/requests")
    Call<Void> sendRequest(@Body Request request,
                           @Header("Authorization") String token);

    @GET("/requests/new/{teamId}")
    Call<List<NewRequest>> getRequests(@Path("teamId") int teamId,
                                       @Header("Authorization") String token);

    @PUT("/requests/{requestId}/{response}")
    Call<Void> requestResponse(@Path("requestId") int requestId,
                               @Path("response") boolean response,
                               @Header("Authorization") String token);

    @PUT("/teams/appearance")
    Call<Void> updateAppearance(@Body AppearanceUpdateBean updateBean,
                                @Header("Authorization") String token);

    @GET("/requests/sent/{teamId}")
    Call<List<NewRequest>> getSentRequests(@Path("teamId") int teamId,
                                           @Header("Authorization") String token);

    @DELETE("/requests/{requestId}")
    Call<Void> deleteRequest(@Path("requestId") int requestId,
                             @Header("Authorization") String token);

    @PUT("/requests/{requestId}")
    Call<Void> removeFromMyRequests(@Path("requestId") int requestId,
                                    @Header("Authorization") String token);

    @PUT("/teams/{teamId}/available")
    Call<Void> updateAvailability(@Path("teamId") int teamId,
                                  @Header("Authorization") String token);

    @POST("/register/refresh")
    Call<JWTInfo> refreshToken(@Body RefreshInfo refreshInfo);
}
