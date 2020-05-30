class Invitation{
    long when;
}

class Ticket {
    int fee;

    public int getFee() {
        return fee;
    }
}

class Bag {
    int amount;
    Invitation invitation;
    Ticket ticket;

    public void plusAmount(int amount) {
        this.amount += amount;
    }

    public void minusAmount(int amount) {
        this.amount -= amount;
    }

    public boolean isInvitated() {
        return invitation;
    }
}

class Audience {
    Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}

class TicketOffice {
    int amount;
    List<Ticket> ticketList = new ArrayList();

    public Ticket getTicket() {
        return ticketList.remove(0);
    }
}

class TicketSeller {
    TicketOffice ticketOffice;

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
}

class Theater {
    TicketSeller seller;
    void onEnter(Audience audience) {
        if(audience.getBag().isInvitated()) {
            Ticket ticket = seller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = seller.getTicketOffice().getTicket();
            seller.getTicketOffice().plusAmount();
            audience.getBag().setTicket(ticket);
            audience.getBag().minusAmount();
        }
    }
}
