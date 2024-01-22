package com.jakobniinja;

import com.godtsoft.diyjava.DIYWindow;

public class MyWindow extends DIYWindow {

  protected char promptForChar(String prompt) {
    char c = ' ';
    boolean valid = false;
    while (!valid) {
      print(prompt);
      String s = input();
      if (s.length() == 1) {
        c = s.charAt(0);
        valid = true;
      } else {
        c = promptForChar(prompt);
      }
    }
    return c;
  }

  public boolean promptForYesNo(String message) {
    print(message);
    return input().contains("y");
  }

  protected String promptForString(String prompt) {
    print(prompt);
    return input();
  }

}
