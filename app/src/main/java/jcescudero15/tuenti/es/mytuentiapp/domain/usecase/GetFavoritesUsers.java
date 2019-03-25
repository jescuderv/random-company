package jcescudero15.tuenti.es.mytuentiapp.domain.usecase;

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
public class GetFavoritesUsers extends UseCase<List<User>, Void> {

    private UserDataSource mRepository;


    @Inject
    GetFavoritesUsers(@Named("executor_thread") Scheduler executorThread, @Named("main_thread") Scheduler
            uiThread, UserRepository userRepository) {
        super(executorThread, uiThread);
        mRepository = userRepository;
    }


    @Override
    protected Observable<List<User>> createObservableUseCase(Void unused) {
        return mRepository.getUserList()
                .concatMap(userList -> Observable
                        .just(userList)
                        .flatMap(Observable::fromIterable)
                        .filter(User::isFavorite)
                        .toList()
                        .toObservable());
    }
}
