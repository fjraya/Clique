package dal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 10/7/17.
 */
public class FriendsRepositoryImpl implements FriendsRepository {
    private List<FriendsRepository> friendsRepositories;

    public FriendsRepositoryImpl() {
        friendsRepositories = new ArrayList<FriendsRepository>();
        friendsRepositories.add(new GithubFriendsRepository());
        friendsRepositories.add(new TwitterFriendsRepository());
    }

    public FriendsRepositoryImpl(List<FriendsRepository> friendsRepositories) {
        this.friendsRepositories = friendsRepositories;
    }

    @Override
    public List<String> getUsersConnected(String userHandle) throws QueryException {
        List<String> users = new ArrayList<>();
        for (FriendsRepository friendsRepository: friendsRepositories) {
            List<String> usersConnected = friendsRepository.getUsersConnected(userHandle);
            if (usersConnected.size() == 0) return usersConnected;
            if (users.size() > 0) users.retainAll(usersConnected);
            else users = usersConnected;
        }
        return users;
    }
}
