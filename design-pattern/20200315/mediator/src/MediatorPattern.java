package net.mossol.practice.design.mediator;

import org.junit.jupiter.api.Test;

public class MediatorPattern {

    @Test
    public void testMediatorPattern() {
        final ChatMediator mediator = new ChatMediatorImpl();
        final User user1 = new UserImpl(mediator, "Amos");
        final User user2 = new UserImpl(mediator, "Harkony");
        final User user3 = new UserImpl(mediator, "KingGodSOO");
        final User user4 = new UserImpl(mediator, "SshPlendid");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);
        mediator.addUser(user4);

        user1.send("TEST");
    }
}
