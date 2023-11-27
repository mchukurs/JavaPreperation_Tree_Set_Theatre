package com.chukurs;

public class BookingAgent {
    public static void main(String[] args) {
        int rows = 10;
        int totalSeats = 100;
        Theatre rodgersNYC = new Theatre("Richard Rodgers", totalSeats, rows);
        rodgersNYC.printSeatMap();
        bookSeat(rodgersNYC,'A',4);
        bookSeat(rodgersNYC,'A',4);
        bookSeat(rodgersNYC,'D',1);
    }

public static void bookSeat(Theatre theatre, char row, int seatNo){
        String seat = theatre.reserveSeat(row,seatNo);
        if(seat!=null){
            System.out.println("Congratulations! Your reserved seat is " + seat);
            theatre.printSeatMap();
        }else {
            System.out.println("Sorry! Unable to reserve "+ row + seatNo);
}



}}
