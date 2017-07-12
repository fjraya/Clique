package dal;

import akka.actor.AbstractActor;

import java.util.List;

/**
 * Created by franciscoraya on 9/7/17.
 */
public class FriendsRepositoryActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, userHandle -> {
                    System.out.println("Received String message in repository: " + userHandle);
                    FriendsRepositoryImpl friendsRepository = new FriendsRepositoryImpl();
                    List<String> neighbours = friendsRepository.getUsersConnected(userHandle);
                    getSender().tell(neighbours, getSelf());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

}
