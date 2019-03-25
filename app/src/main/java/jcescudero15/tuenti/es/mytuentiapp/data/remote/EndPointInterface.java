package jcescudero15.tuenti.es.mytuentiapp.data.remote;

import io.reactivex.Observable;
import jcescudero15.tuenti.es.mytuentiapp.data.remote.dto.UserDTO;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPointInterface {

    @GET("/")
    Observable<UserDTO> getRandomUserList(@Query("results") Integer results);

}
