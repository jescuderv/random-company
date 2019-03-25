package jcescudero15.tuenti.es.mytuentiapp.domain.usecase;

import android.location.Location;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.repository.UserRepository;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.utils.UseCase;

@Singleton
public class GetCloseUsers extends UseCase<List<User>, GetCloseUsers.Params> {

    private final static float DISTANCE_BETWEEN_LOCATIONS = 3000;


    private UserDataSource mRepository;


    @Inject
    GetCloseUsers(@Named("executor_thread") Scheduler executorThread, @Named("main_thread") Scheduler
            uiThread, UserRepository userRepository) {
        super(executorThread, uiThread);
        mRepository = userRepository;
    }


    @Override
    protected Observable<List<User>> createObservableUseCase(Params params) {
        return mRepository.getUserList()
                .concatMap(userList -> Observable
                        .just(userList)
                        .flatMap(Observable::fromIterable)
                        .filter(user -> {
                            Location userLocation = new Location("");
                            userLocation.setLatitude(user.getLatitude());
                            userLocation.setLongitude(user.getLongitude());

                            // distance in km
                            float distance = params.getCurrentLocation().distanceTo(userLocation) / 1000;

                            return distance <= DISTANCE_BETWEEN_LOCATIONS;
                        })
                        .toList()
                        .toObservable());
    }

    public final static class Params {
        private Location currentLocation;

        public Params(Location currentLocation) {
            this.currentLocation = currentLocation;
        }

        Location getCurrentLocation() {
            return currentLocation;
        }
    }
}
