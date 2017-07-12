package dal;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by franciscoraya on 10/7/17.
 */
public class GithubFriendsRepository implements FriendsRepository {
    private OrganizationService organizationService;
    private String token = "f5809a0a5043bba90930242aade90b80992989bc";


    public GithubFriendsRepository() {
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(token);
        this.organizationService = new OrganizationService(client);
    }


    public GithubFriendsRepository(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public List<String> getUsersConnected(String userHandle) throws QueryException {
        try {
            List<User> organizations = organizationService.getOrganizations(userHandle);
            Set<String> userHandles = new HashSet<>();
            for (User user : organizations) {
                List<User> members = organizationService.getMembers(user.getLogin());
                for (User member : members) {
                    userHandles.add(member.getLogin());
                }
            }
            return new ArrayList<>(userHandles);
        }
        catch (IOException ioe) {
            if (!ioe.getMessage().contains("(404")) throw new QueryException(ioe.getMessage());
        }
        return new ArrayList<>();
    }
}
