package dal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class TwitterFriendsRepository implements FriendsRepository {
    private SocialNetworkWrapper innerWrapper;

    public TwitterFriendsRepository(SocialNetworkWrapper innerWrapper) {
        this.innerWrapper = innerWrapper;
    }



    public TwitterFriendsRepository() {
        this.innerWrapper = new TwitterSocialNetworkWrapper();
    }

    public List<String> getUsersConnected(String userHandle) throws QueryException {
        try {

            List<String> followers = innerWrapper.getFollowersScreenName(userHandle);
            List<String> friends = innerWrapper.getFriendsScreenName(userHandle);
            ArrayList<String> intersection = new ArrayList<String>(followers);
            intersection.retainAll(friends);
            return intersection;
        } catch (SocialNetWorkWrapperException e) {
            throw new QueryException(e.getMessage());
        }

    }
}
