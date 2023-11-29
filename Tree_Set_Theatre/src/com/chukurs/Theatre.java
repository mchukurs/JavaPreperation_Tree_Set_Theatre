package com.chukurs;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Theatre {
    private String theatreName;
    private int seatCountPerRow;//always less than 27
    private NavigableSet<Seat> seats;

    public Theatre(String theatreName, int totalSeats, int rowCount) {
        this.theatreName = theatreName;
        this.seatCountPerRow = totalSeats / rowCount;
        seats = new TreeSet<>();
        for (int i = 0; i < totalSeats; i++) {
            //below will give us A for first row, 'B' for next row etc.
            char rowChar = (char) (i / seatCountPerRow + (int) 'A');
            //adding 1 as we do not want them to start at 0
            int seatInRow = i % seatCountPerRow + 1;
            seats.add(new Seat(rowChar, seatInRow));
        }

    }

    private boolean validate(int seatCount, char minRow, char maxRow, int minSeat, int maxSeat) {

        boolean result = (minSeat > 0 && seatCountPerRow >= seatCount && (maxSeat - minSeat + 1) >= seatCount);
        result = result && seats.contains(new Seat(minRow, minSeat));
        if (!result) {
            System.out.printf("Invalid! %1$d seats between " +
                            "%2$c[%3$d-%4$d]-%5$c[%3$d-%4$d] Try again",
                    seatCount, minRow , minSeat,maxSeat, maxRow);
            //provide info what can be used
            System.out.printf(": Seat must be between %s and %s%n",
                    seats.first().seatNumber, seats.last().seatNumber);
        }
        return result;
    }

    public Set<Seat> reserveMultipleSeats(int seatCount, char minRow, char maxRow, int minSeat, int maxSeat) {
        //to get the last ROW from our seats
        char lastValid = seats.last().seatNumber.charAt(0);
        //if requested row is smaller than last then we use that, if it's outside the seat chart then we use the last row.
        maxRow = (maxRow < lastValid) ? maxRow : lastValid;

        if (!validate(seatCount, minRow, maxRow, minSeat, maxSeat)) {
            return null;
        }

        NavigableSet<Seat> selected = null;
        //here go through each ROW from out seat chart that matched the requirements from agent
        for (char letter = minRow; letter <= maxRow; letter++) {
            //get the seats in the row currently looped over
            NavigableSet<Seat> contiguous = seats.subSet(
                    new Seat(letter, minSeat),
                    true,
                    new Seat(letter, maxSeat),
                    true);
            int index = 0;
            //will get the 1st unreserved seat
            Seat first = null;
            for (Seat current : contiguous) {
                if (current.reserved) {
                    //reset index and continue with next seat in the set (as not enough unreserved seats found)
                    index = 0;
                    continue;
                }
                //set first as current when found the very first unreserved
                first = (index == 0) ? current : first;
                //check if found seat count is as requested (increment as not reserved)
                if (++index == seatCount) {
                    //get the contiguous seats in the row as per requirement
                    selected = contiguous.subSet(first, true, current, true);
                    break;
                }
            }
            if (selected != null) {
                //break when selected is populated with required seats
                break;
            }
        }
        Set<Seat> reservedSeats = null;
        if (selected != null) {
            selected.forEach(s -> s.reserved = true);
            //create a copy of set
            reservedSeats = new TreeSet<>(selected);
        }
        return reservedSeats;
    }

    public String reserveSeat(char row, int seat) {

        Seat requestedSeat = new Seat(row, seat);
        Seat requested = seats.floor(requestedSeat);
        if (requested == null || !requested.seatNumber.equals(requestedSeat.seatNumber)) {
            System.out.println("No such seat: " + requestedSeat);
            System.out.printf(": Seat must be between %s and %s%n", seats.first().seatNumber, seats.last().seatNumber);

        } else {
            if (!requested.reserved) {
                requested.reserved = true;
                return requested.seatNumber;
            } else {
                System.out.println("Seat is already reserved! ");
            }
        }
        return null;
    }

    public void printSeatMap() {
        String separatorLine = "-".repeat(90);
        System.out.printf("%1$s%n%2$s Seat Map%n%1$s%n", separatorLine,
                theatreName);
        System.out.println(separatorLine);
        int index = 0;
        for (Seat seat : seats) {
            System.out.printf("%-8s%s",
                    seat.seatNumber + ((seat.reserved) ? "(\u25CF)" : ""),
                    ((index++ + 1) % seatCountPerRow == 0) ? "\n" : "");
        }
    }

    class Seat implements Comparable<Seat> {
        private String seatNumber;//A-Z
        private boolean reserved;

        public Seat(char rowChar, int seatNo) {
            this.seatNumber = "%c%03d".formatted(rowChar, seatNo).toUpperCase();
        }

        @Override
        public String toString() {
            return this.seatNumber;
        }

        @Override
        public int compareTo(Seat o) {
            return seatNumber.compareTo(o.seatNumber);
        }
    }
}