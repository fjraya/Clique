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
    public void test_getUsersConnected_calledWithNoBidirectionalRelationShip_returnEmptyList() throws SocialNetWorkWrapperException, QueryException {
        List<String> followers = Arrays.asList("516478373", "863434009048739840", "1710537350", "92586914", "821409222399262721", "3796287012","249619704");

        List<String> friends = Arrays.asList("31676814", "360557311", "4265275695", "2794858065", "767660573299449856");
        List<String> expected = new ArrayList<String>();
        exerciseGetUsersConnectedAndVerify(followers, friends, expected);
    }
    

    @Test(groups = {"unit"})
    public void test_getUsersConnected_calledWithSomeBidirectionRelationShip_returnListWithPeopleThatIsMemberOfTwoLists() throws SocialNetWorkWrapperException, QueryException {
        List<String> followers = Arrays.asList("516478373", "863434009048739840", "1710537350", "92586914", "767660573299449856", "3796287012", "249619704");
        List<String> friends = Arrays.asList("31676814", "516478373", "4265275695", "1710537350", "767660573299449856");
        List<String> expected = Arrays.asList("516478373", "1710537350", "767660573299449856");
        exerciseGetUsersConnectedAndVerify(followers, friends, expected);
    }

    @Test(groups = {"unit"}, expectedExceptions = QueryException.class)
    public void test_getUsersConnected_calledWithInnerWrapperThatThrowsException_throwsQueryException() throws SocialNetWorkWrapperException, QueryException {
        Mockito.when(wrapperStub.getFollowersScreenName(testUserHandle)).thenThrow(SocialNetWorkWrapperException.class);
        sut.getUsersConnected(testUserHandle);
    }

    private void exerciseGetUsersConnectedAndVerify(List<String> followers, List<String> friends, List<String> expected) throws QueryException, SocialNetWorkWrapperException {
        Mockito.when(wrapperStub.getFollowersScreenName(testUserHandle)).thenReturn(followers);
        Mockito.when(wrapperStub.getFriendsScreenName(testUserHandle)).thenReturn(friends);
        List<String> actual = sut.getUsersConnected(testUserHandle);
        Assert.assertEquals(actual, expected);
    }
}
