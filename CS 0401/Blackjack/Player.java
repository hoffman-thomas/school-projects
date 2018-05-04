import java.util.Scanner;
import java.io.*;

public class Player {

    private String username;
    private int password;
    private double money;
    private int handsPlayed;
    private int handsWon;
    private int bjCount;

    //new user consctructor
    public Player(String _username, int _password) {
      username = _username;
      password = _password;
      money = 500.0;
      handsPlayed = 0;
      handsWon = 0;
      bjCount = 0;
    }

    //returning user consctructor
    public Player(String _username, int _password, double _money, int _handsPlayed, int _handsWon, int _bjCount) {
      username = _username;
      password = _password;
      money = _money;
      handsPlayed = _handsPlayed;
      handsWon = _handsWon;
      bjCount = _bjCount;
    }

    //Getters
    public String getUsername(){
      return username;
    }

    public int getPassword(){
      return password;
    }

    public Double getMoney(){
      return money;
    }

    public int getHandsPlayed(){
      return handsPlayed;
    }

    public int getHandsWon(){
      return handsWon;
    }

    public int getBjCount(){
      return bjCount;
    }


    //Setters
    public void setMoney(double _money){
      money = _money;
    }

    public void incHandsWon(){
      handsWon++;
    }

    public void incHandsPlayed(){
      handsPlayed++;
    }

    public void incBjCount(){
      bjCount++;
    }

}
