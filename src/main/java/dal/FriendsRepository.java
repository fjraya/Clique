package dal;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class FriendsRepository  {
    private SocialNetworkWrapper innerWrapper;

    public FriendsRepository(SocialNetworkWrapper innerWrapper) {
        this.innerWrapper = innerWrapper;
    }



    public FriendsRepository() {
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
