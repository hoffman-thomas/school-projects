


import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Ballot extends JPanel{

  //initializes the variables for use in object
  private int id;
  private String title;
  private String[] cand;

  private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
  private JLabel header;

  //construct the obj
  public Ballot(int _id, String _title, String[] _cand){
    id = _id;
    title = _title;
    cand = _cand;

    header = new JLabel(_title);

    for(int i = 0; i < _cand.length; i++){
      JButton jb = new JButton(_cand[i]);
      buttonArray.add(jb);
    }

  }

  public JLabel getHeader(){

    return header;
  }

  public ArrayList<JButton> getButtons(){

    return buttonArray;
  }

  public int getId(){
    return id;
  }

  public String getTitle(){
    return title;
  }

  public String getCand(int i){
    return cand[i];
  }
  public String[] getAbsolutelyCand(){
    return cand;
  }

}
