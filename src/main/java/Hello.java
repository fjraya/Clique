import dal.SocialNetWorkWrapperException;
import dal.TwitterSocialNetworkWrapper;

import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class Hello {
    public static void main (String [] args) throws SocialNetWorkWrapperException {
        TwitterSocialNetworkWrapper wrapper = new TwitterSocialNetworkWrapper();
        List<Long> followers = wrapper.getFollowersScreenName("soyelrayan");
        List<Long> friends = wrapper.getFriendsScreenName("soyelrayan");
        for (Long item: followers) {
            System.out.println(item);
        }
        System.out.println("=============================================================================");
        for (Long item: friends) {
            System.out.println(item);
        }
    }
}
