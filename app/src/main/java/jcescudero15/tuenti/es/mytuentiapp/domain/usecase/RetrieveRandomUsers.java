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
public class RetrieveRandomUsers extends UseCase<List<User>, RetrieveRandomUsers.Params> {

    private UserDataSource mRepository;


    @Inject
    RetrieveRandomUsers(@Named("executor_thread") Scheduler executorThread, @Named("main_thread") Scheduler
            uiThread, UserRepository userRepository) {
        super(executorThread, uiThread);
        mRepository = userRepository;
    }


    @Override
    protected Observable<List<User>> createObservableUseCase(Params params) {
        return mRepository.retrieveUserList(params.getResults());
    }


    public static final class Params {

        private Integer results;

        public Params(Integer results) {
            this.results = results;
        }

        Integer getResults() {
            return results;
        }
    }
}
