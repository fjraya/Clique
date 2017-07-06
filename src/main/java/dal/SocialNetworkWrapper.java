package dal;

import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public interface SocialNetworkWrapper {
    public List<Long> getFollowersScreenName(String userHandle) throws SocialNetWorkWrapperException;
    public List<Long> getFriendsScreenName(String userHandle) throws SocialNetWorkWrapperException;
}
