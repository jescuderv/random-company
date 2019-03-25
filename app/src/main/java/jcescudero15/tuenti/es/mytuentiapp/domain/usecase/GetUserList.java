package jcescudero15.tuenti.es.mytuentiapp.domain.usecase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import jcescudero15.tuenti.es.mytuentiapp.data.UserDataSource;
import jcescudero15.tuenti.es.mytuentiapp.data.repository.UserRepository;
import jcescudero15.tuenti.es.mytuentiapp.domain.model.User;
import jcescudero15.tuenti.es.mytuentiapp.ui.UserFilterSort;
import jcescudero15.tuenti.es.mytuentiapp.utils.UseCase;

import static jcescudero15.tuenti.es.mytuentiapp.utils.Utils.containsMatchText;

@Singleton
public class GetUserList extends UseCase<List<User>, GetUserList.Params> {

    private UserDataSource mRepository;


    @Inject
    GetUserList(@Named("executor_thread") Scheduler executorThread, @Named("main_thread") Scheduler
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
                            String queryText = params.getFilterQuery();
                            return (containsMatchText(queryText, user.getName()) ||
                                    containsMatchText(queryText, user.getLastName()) ||
                                    containsMatchText(queryText, user.getEmail()));
                        })
                        .toList()
                        .map(userListSorted -> {
                            switch (params.getSorted()) {
                                case SORT_BY_NAME:
                                    Collections.sort(userListSorted,
                                            (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                                    return userListSorted;

                                case SORT_BY_GENDER:
                                    Collections.sort(userListSorted,
                                            (o1, o2) -> o1.getGender().compareToIgnoreCase(o2.getGender()));
                                    return userListSorted;

                                default:
                                    return userListSorted;
                            }
                        })
                        .toObservable());
    }


    public final static class Params {
        private UserFilterSort sorted;
        private String filterQuery;

        public Params(UserFilterSort sorted, String filterQuery) {
            this.sorted = sorted;
            this.filterQuery = filterQuery;
        }

        UserFilterSort getSorted() {
            return sorted;
        }

        String getFilterQuery() {
            return filterQuery;
        }
    }
}
