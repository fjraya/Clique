package dal;

import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public interface SocialNetworkWrapper {
    public List<String> getFollowersScreenName(String userHandle) throws SocialNetWorkWrapperException;
    public List<String> getFriendsScreenName(String userHandle) throws SocialNetWorkWrapperException;
}
