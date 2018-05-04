public class Card{
  String name;
  int faceValue;
  int isAce;

  public Card(int card) {
    switch (card)
    {
      //Clubs
      case 0:
      name = "2C";
      faceValue = 2;
      isAce = 0;
      break;

      case 1:
      name = "3C";
      faceValue = 3;
      isAce = 0;
      break;

      case 2:
      name = "4C";
      faceValue = 4;
      isAce = 0;
      break;

      case 3:
      name = "5C";
      faceValue = 5;
      isAce = 0;
      break;

      case 4:
      name = "6C";
      faceValue = 6;
      isAce = 0;
      break;

      case 5:
      name = "7C";
      faceValue = 7;
      isAce = 0;
      break;

      case 6:
      name = "8C";
      faceValue = 8;
      isAce = 0;
      break;

      case 7:
      name = "9C";
      faceValue = 9;
      isAce = 0;
      break;

      case 8:
      name = "TC";
      faceValue = 10;
      isAce = 0;
      break;

      case 9:
      name = "JC";
      faceValue = 10;
      isAce = 0;
      break;

      case 10:
      name = "QC";
      faceValue = 10;
      isAce = 0;
      break;

      case 11:
      name = "KC";
      faceValue = 10;
      isAce = 0;
      break;

      case 12:
      name = "AC";
      faceValue = 11;
      isAce = 1;
      break;

      //Hearts
      case 13:
      name = "2H";
      faceValue = 2;
      isAce = 0;
      break;

      case 14:
      name = "3H";
      faceValue = 3;
      isAce = 0;
      break;

      case 15:
      name = "4H";
      faceValue = 4;
      isAce = 0;
      break;

      case 16:
      name = "5H";
      faceValue = 5;
      isAce = 0;
      break;

      case 17:
      name = "6H";
      faceValue = 6;
      isAce = 0;
      break;

      case 18:
      name = "7H";
      faceValue = 7;
      isAce = 0;
      break;

      case 19:
      name = "8H";
      faceValue = 8;
      isAce = 0;
      break;

      case 20:
      name = "9H";
      faceValue = 9;
      isAce = 0;
      break;

      case 21:
      name = "TH";
      faceValue = 10;
      isAce = 0;
      break;

      case 22:
      name = "JH";
      faceValue = 10;
      isAce = 0;
      break;

      case 23:
      name = "QH";
      faceValue = 10;
      isAce = 0;
      break;

      case 24:
      name = "KH";
      faceValue = 10;
      isAce = 0;
      break;

      case 25:
      name = "AH";
      faceValue = 11;
      isAce = 1;
      break;

      //Diamonds

      case 26:
      name = "2D";
      faceValue = 2;
      isAce = 0;
      break;

      case 27:
      name = "3D";
      faceValue = 3;
      isAce = 0;
      break;

      case 28:
      name = "4D";
      faceValue = 4;
      isAce = 0;
      break;

      case 29:
      name = "5D";
      faceValue = 5;
      isAce = 0;
      break;

      case 30:
      name = "6D";
      faceValue = 6;
      isAce = 0;
      break;

      case 31:
      name = "7D";
      faceValue = 7;
      isAce = 0;
      break;

      case 32:
      name = "8D";
      faceValue = 8;
      isAce = 0;
      break;

      case 33:
      name = "9D";
      faceValue = 9;
      isAce = 0;
      break;

      case 34:
      name = "TD";
      faceValue = 10;
      isAce = 0;
      break;

      case 35:
      name = "JD";
      faceValue = 10;
      isAce = 0;
      break;

      case 36:
      name = "QD";
      faceValue = 10;
      isAce = 0;
      break;

      case 37:
      name = "KD";
      faceValue = 10;
      isAce = 0;
      break;

      case 38:
      name = "AD";
      faceValue = 11;
      isAce = 1;
      break;

      //Spades

      case 39:
      name = "2S";
      faceValue = 2;
      isAce = 0;
      break;

      case 40:
      name = "3S";
      faceValue = 3;
      isAce = 0;
      break;

      case 41:
      name = "4S";
      faceValue = 4;
      isAce = 0;
      break;

      case 42:
      name = "5S";
      faceValue = 5;
      isAce = 0;
      break;

      case 43:
      name = "6S";
      faceValue = 6;
      isAce = 0;
      break;

      case 44:
      name = "7S";
      faceValue = 7;
      isAce = 0;
      break;

      case 45:
      name = "8S";
      faceValue = 8;
      isAce = 0;
      break;

      case 46:
      name = "9S";
      faceValue = 9;
      isAce = 0;
      break;

      case 47:
      name = "TS";
      faceValue = 10;
      isAce = 0;
      break;

      case 48:
      name = "JS";
      faceValue = 10;
      isAce = 0;
      break;

      case 49:
      name = "QS";
      faceValue = 10;
      isAce = 0;
      break;

      case 50:
      name = "KS";
      faceValue = 10;
      isAce = 0;
      break;

      case 51:
      name = "AS";
      faceValue = 11;
      isAce = 1;
      break;

    }

  }

  public int getAce(){
    return isAce;
  }

  public int getFaceValue(){
    return faceValue;
  }

  public String getName(){
    return name;
  }

  //get Name
}
