package dal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class FriendsRepository {
    private SocialNetworkWrapper innerWrapper;

    public FriendsRepository(SocialNetworkWrapper innerWrapper) {
        this.innerWrapper = innerWrapper;
    }

    public FriendsRepository() {
        this.innerWrapper = new TwitterSocialNetworkWrapper();
    }

    public List<Long> getFriends(String userHandle) throws QueryException {
        try {
            List<Long> followers = innerWrapper.getFollowersScreenName(userHandle);
            List<Long> friends = innerWrapper.getFriendsScreenName(userHandle);
            ArrayList<Long> intersection = new ArrayList<Long>(followers);
            intersection.retainAll(friends);
            return intersection;
        } catch (SocialNetWorkWrapperException e) {
            throw new QueryException(e.getMessage());
        }

    }
}
