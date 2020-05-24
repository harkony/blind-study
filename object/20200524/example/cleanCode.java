package net.mossol.practice.object;

public class cleanCode {
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

        public int hold(Ticket ticket) {
            if(isInvitated()) {
                setTicket(ticket);
            } else {
                setTicket(ticket);
                minusAmount();
            }
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

        public int buy(Ticket ticket) {
            bag.hold(ticket);
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

        public void sellTo(Audience audience) {
            Ticket ticket = ticketOffice.getTicket();

        }
    }

    class Theater {
        TicketSeller seller;

        void onEnter(Audience audience) {
            seller.sellTo(audience);
        }
    }
}

