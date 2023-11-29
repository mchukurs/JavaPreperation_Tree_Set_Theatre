package com.chukurs;

public class BookingAgent {
    public static void main(String[] args) {
        int rows = 10;
        int totalSeats = 100;
        Theatre rodgersNYC = new Theatre("Richard Rodgers", totalSeats, rows);
        rodgersNYC.printSeatMap();
        bookSeat(rodgersNYC, 'A', 4);
        bookSeat(rodgersNYC, 'A', 4);
        bookSeat(rodgersNYC, 'D', 1);
        //testing multiple seat booking

        bookSeats(rodgersNYC,4,'B',3,10);
        bookSeats(rodgersNYC,6,'B','C',3,10);
        bookSeats(rodgersNYC,4,'B',1,10);
        bookSeats(rodgersNYC,4,'B','C',1,10);
        bookSeats(rodgersNYC,1,'B','C',1,10);
        bookSeats(rodgersNYC,4,'M','Z',1,10);
        bookSeats(rodgersNYC,10,'A','E',1,10);
    }

    public static void bookSeat(Theatre theatre, char row, int seatNo) {
        String seat = theatre.reserveSeat(row, seatNo);
        if (seat != null) {
            System.out.println("Congratulations! Your reserved seat is " + seat);
            theatre.printSeatMap();
        } else {
            System.out.println("Sorry! Unable to reserve " + row + seatNo);
        }
    }

    public static void bookSeats(Theatre theatre, int ticketCount, char minRow, char maxRow, int minSeatNo, int maxSeatNo) {

        var seats = theatre.reserveMultipleSeats(ticketCount, minRow, maxRow, minSeatNo, maxSeatNo);
        if (seats != null) {
            System.out.println("Congratulations! Your reserved seats are " + seats);
            theatre.printSeatMap();

        } else {
            System.out.println("Sorry! No matching contiguous seats in rows: " + minRow + " - " + maxRow);
        }
    }

    //overloaded version to allow searching for seats in the same row
    public static void bookSeats(Theatre theatre, int ticketCount, char minRow, int minSeatNo, int maxSeatNo) {
        bookSeats(theatre, ticketCount, minRow, minRow, minSeatNo, maxSeatNo);
    }


}
