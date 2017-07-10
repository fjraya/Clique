package dal;

import java.util.List;

/**
 * Created by franciscoraya on 10/7/17.
 */
public interface FriendsRepository {
    List<String> getUsersConnected(String userHandle) throws QueryException;
}
