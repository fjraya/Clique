import dal.FriendsRepository;
import dal.QueryException;
import dal.SocialNetWorkWrapperException;
import dal.SocialNetworkWrapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class FriendsRepositoryUnitTest {
    private FriendsRepository sut;
    private SocialNetworkWrapper wrapperStub;
    private String testUserHandle = "soyelrayan";
    @BeforeMethod
    public void setUp()
    {
        wrapperStub = Mockito.mock(SocialNetworkWrapper.class);
        sut = new FriendsRepository(wrapperStub);
    }

    @Test(groups = {"unit"})
    public void test_getFriends_calledWithNoBidirectionalRelationShip_returnEmptyList() throws SocialNetWorkWrapperException, QueryException {
        List<Long> followers = Arrays.asList(new Long("516478373"), new Long("863434009048739840"), new Long("1710537350"), new Long("92586914"), new Long("821409222399262721"), new Long("3796287012"), new Long("249619704"));

        List<Long> friends = Arrays.asList(new Long("31676814"), new Long("360557311"), new Long("4265275695"), new Long("2794858065"), new Long("767660573299449856"));
        List<Long> expected = new ArrayList<Long>();
        exerciseGetFriendsAndVerify(followers, friends, expected);
    }
    

    @Test(groups = {"unit"})
    public void test_getFriends_calledWithSomeBidirectionRelationShip_returnListWithPeopleThatIsMemberOfTwoLists() throws SocialNetWorkWrapperException, QueryException {
        List<Long> followers = Arrays.asList(new Long("516478373"), new Long("863434009048739840"), new Long("1710537350"), new Long("92586914"), new Long("767660573299449856"), new Long("3796287012"), new Long("249619704"));
        List<Long> friends = Arrays.asList(new Long("31676814"), new Long("516478373"), new Long("4265275695"), new Long("1710537350"), new Long("767660573299449856"));
        List<Long> expected = Arrays.asList(new Long("516478373"), new Long("1710537350"), new Long("767660573299449856"));
        exerciseGetFriendsAndVerify(followers, friends, expected);
    }

    @Test(groups = {"unit"}, expectedExceptions = QueryException.class)
    public void test_getFriends_calledWithInnerWrapperThatThrowsException_throwsQueryException() throws SocialNetWorkWrapperException, QueryException {
        Mockito.when(wrapperStub.getFollowersScreenName(testUserHandle)).thenThrow(SocialNetWorkWrapperException.class);
        sut.getFriends(testUserHandle);
    }

    private void exerciseGetFriendsAndVerify(List<Long> followers, List<Long> friends, List<Long> expected) throws QueryException, SocialNetWorkWrapperException {
        Mockito.when(wrapperStub.getFollowersScreenName(testUserHandle)).thenReturn(followers);
        Mockito.when(wrapperStub.getFriendsScreenName(testUserHandle)).thenReturn(friends);
        List<Long> actual = sut.getFriends(testUserHandle);
        Assert.assertEquals(actual, expected);
    }
}
