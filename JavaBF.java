import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStreamReader;

public class JavaBF {
  public static void main(String[] args) throws IOException {
    String prog;
    InputStreamReader stdin = new InputStreamReader(System.in);
    try {
      prog = new String(Files.readAllBytes(Paths.get(args[0]))).replaceAll("[^\\>\\<\\+\\-\\,\\.\\[\\]]+", "");
    } catch (IOException e) {
      prog = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.++++++++++++++++++++++++++++++++++++++++++++.++++++.---------.--------.++++++++++.-----------------------------------------------------------------------.+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.------------------------------------------------------------------------------------.+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+.-----------.+++++++++.------------------------------------------------------------------------------.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+++.+++.-------.-------------------------------------------------------.------------------------------------.";
    }

    int pPtr = 0;
    int tPtr = 0;
    HashMap<Integer, Integer> tape        = new HashMap<>();
    HashMap<Integer, Integer> openToClose = new HashMap<>();
    HashMap<Integer, Integer> closeToOpen = new HashMap<>();
    for (int openPos = 0; openPos < prog.length(); openPos++) {
      if (prog.charAt(openPos) == '[') {
        int closePos, count;
        for (closePos = openPos, count = 1; count > 0;) {
          char character;
          if      ((character = prog.charAt(++closePos)) == '[') count++;
          else if  (character == ']')                            count--;
        }
        openToClose.put(openPos, closePos);
        closeToOpen.put(closePos, openPos);
      }
    }

    while (pPtr < prog.length()) {
      switch (prog.charAt(pPtr)) {
        case '>':
          tPtr++;
          break;
        case '<':
          tPtr--;
          break;
        case '+':
          if (tape.containsKey(tPtr)) tape.put(tPtr, tape.get(tPtr) + 1);
          else                        tape.put(tPtr, 1);
          break;
        case '-':
          if (tape.containsKey(tPtr)) tape.put(tPtr, tape.get(tPtr) - 1);
          else                        tape.put(tPtr, -1);
          break;
        case ',':
          tape.put(tPtr, stdin.read());
          break;
        case '.':
          System.out.print(Character.toChars(tape.get(tPtr)));
          break;
        case '[':
          if (!tape.containsKey(tPtr)) tape.put(tPtr, 0);
          if (tape.get(tPtr) == 0)
            pPtr = openToClose.get(pPtr);
          break;
        case ']':
          pPtr = closeToOpen.get(pPtr);
          continue;
      }
      pPtr++;
    }
  }
}
