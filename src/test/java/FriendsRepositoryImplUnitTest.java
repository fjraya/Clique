import dal.FriendsRepository;
import dal.FriendsRepositoryImpl;
import dal.GithubFriendsRepository;
import dal.QueryException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by franciscoraya on 10/7/17.
 */
public class FriendsRepositoryImplUnitTest {

    @DataProvider(name = "getUsersConnectedDataProvider")
    public Object[][] getUsersConnectedDataProvider() {

        return new Object[][]{{new ArrayList<>(Arrays.asList("516478373", "1710537350", "767660573299449856", "79685")), new ArrayList<>(Arrays.asList("1710537350", "79685", "767660573299449856", "30202392")), new ArrayList<>(Arrays.asList("1710537350", "79685", "767660573299449856"))},
                {new ArrayList<>(), new ArrayList<>(Arrays.asList("1710537350", "79685", "767660573299449856", "30202392")), new ArrayList<>()},
                {new ArrayList<>(Arrays.asList("1710537350", "79685", "767660573299449856", "30202392")), new ArrayList<>(), new ArrayList<>()},
                {new ArrayList<>(Arrays.asList("516478373", "1710537350", "767660573299449856", "79685")), new ArrayList<>(Arrays.asList("2", "4", "6")), new ArrayList<>()}
        };
    }


    @Test(groups = {"unit"}, dataProvider = "getUsersConnectedDataProvider")
    public void test_getUsersConnected_calledWithTwoInnerRepositories_returnIntersectionOfAllTheResults(List<String> stubResult1, List<String> stubResult2, List<String> expected) throws QueryException {
        String testUserHandle = "testUser";
        FriendsRepository friendsRepository1Stub = mock(FriendsRepository.class);
        FriendsRepository friendsRepository2Stub = mock(FriendsRepository.class);
        List<FriendsRepository> initialRepositories = new ArrayList<FriendsRepository>();
        initialRepositories.add(friendsRepository1Stub);
        initialRepositories.add(friendsRepository2Stub);
        when(friendsRepository1Stub.getUsersConnected(testUserHandle)).thenReturn(stubResult1);
        when(friendsRepository2Stub.getUsersConnected(testUserHandle)).thenReturn(stubResult2);
        FriendsRepositoryImpl sut = new FriendsRepositoryImpl(initialRepositories);
        List<String> actual = sut.getUsersConnected(testUserHandle);
        Assert.assertEqualsNoOrder(actual.toArray(), expected.toArray());
    }

}
