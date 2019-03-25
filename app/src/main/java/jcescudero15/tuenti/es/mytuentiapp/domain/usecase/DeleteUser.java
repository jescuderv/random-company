package jcescudero15.tuenti.es.mytuentiapp.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.repository.UserRepository;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.utils.CompletableUseCase;

@Singleton
public class DeleteUser extends CompletableUseCase<DeleteUser.Params> {

    private UserDataSource mRepository;


    @Inject
    DeleteUser(@Named("executor_thread") Scheduler executorThread, @Named("main_thread") Scheduler
            uiThread, UserRepository userRepository) {
        super(executorThread, uiThread);
        mRepository = userRepository;
    }

    @Override
    protected Completable createUseCaseCompletable(Params params) {
        return mRepository.deleteUser(params.getUser());
    }

    public static final class Params {

        private User user;

        public Params(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
