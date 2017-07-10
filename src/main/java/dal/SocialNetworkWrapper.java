package dal;

import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public interface SocialNetworkWrapper {
    List<String> getFollowersScreenName(String userHandle) throws SocialNetWorkWrapperException;
    List<String> getFriendsScreenName(String userHandle) throws SocialNetWorkWrapperException;
}
