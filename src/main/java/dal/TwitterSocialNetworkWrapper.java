package dal;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 6/7/17.
 */


interface AssociationStrategy {
    PagableResponseList<User> getRelatedUsers(String userHandle, long cursor) throws TwitterException;
}

abstract class BaseAssociationStrategy implements AssociationStrategy {
    protected Twitter twitter;

    public BaseAssociationStrategy(Twitter twitter) {
        this.twitter = twitter;
    }
}


class FollowersAssociationStrategy extends BaseAssociationStrategy {

    public FollowersAssociationStrategy(Twitter twitter) {
        super(twitter);
    }

    public PagableResponseList<User> getRelatedUsers(String userHandle, long cursor) throws TwitterException {
        return twitter.getFollowersList(userHandle, cursor);
    }
}

class FriendsAssociationStrategy extends BaseAssociationStrategy {

    public FriendsAssociationStrategy(Twitter twitter) {
        super(twitter);
    }

    public PagableResponseList<User> getRelatedUsers(String userHandle, long cursor) throws TwitterException {
        return twitter.getFriendsList(userHandle, -1);
    }
}


public class TwitterSocialNetworkWrapper implements SocialNetworkWrapper {
    public static final String CONSUMER_KEY = "IHFnhAzF2QpWZNW1tLx4zs5oX";
    public static final String CONSUMER_SECRET = "Kcer1LIMRQa7e8TbQKlGrPdwxBP1Cet2IaBrOQExWER7SlaOG6";
    public static final String ACCESS_TOKEN = "355736571-dcj6JS5x1FGYMVA0tyRhPZmtzPOFBRuKEaLZn54L";
    public static final String ACCESS_TOKEN_SECRET = "xEqp9nfXWXH8fZMIKuh1Zi8TcONrCTdyI8Bj6MZThI4Z7";
    private Twitter twitter;

    public TwitterSocialNetworkWrapper() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public List<String> getFollowersScreenName(String userHandle) throws SocialNetWorkWrapperException {
        return getRelatedUsers(userHandle, new FollowersAssociationStrategy(twitter));
    }


    public List<String> getFriendsScreenName(String userHandle) throws SocialNetWorkWrapperException {
        return getRelatedUsers(userHandle, new FriendsAssociationStrategy(twitter));
    }

    private List<String> getRelatedUsers(String userHandle, AssociationStrategy relation) throws SocialNetWorkWrapperException {
        try {
            long cursor = -1;
            List<String> userHandles = new ArrayList<>();
            PagableResponseList<User> users = relation.getRelatedUsers(userHandle, cursor);
            for (User user : users) {
                userHandles.add(user.getScreenName());
            }
            return userHandles;
        } catch (TwitterException e) {
            throw new SocialNetWorkWrapperException(e.getMessage());
        }

    }
}
