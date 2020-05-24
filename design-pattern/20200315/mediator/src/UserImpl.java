package net.mossol.practice.design.mediator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserImpl extends User {

    public UserImpl(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String msg) {
        log.info("{} : Sending Message : {}", name, msg);
        mediator.sendMessage(msg, this);
    }

    @Override
    public void receive(String msg) {
        log.info("{} : Receiving Message : {}", name, msg);
    }
}
