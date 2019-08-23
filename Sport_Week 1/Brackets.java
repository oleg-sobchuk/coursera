public class Brackets {


    boolean chek(String s) {
        char[] balance = new char[s.length()];
        int index = 0;
        for(char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[') {
                balance[index] = ch;
                index++;
            }
            if (ch == ')' || ch == ']') {
                if (index == 0) {
                    return false;
                }

                //char temp = --ch;
                if (ch == ']') {
                    if (balance[index-1] == '[') {
                        balance[index] = ' ';
                        index--;
                    }else {
                        return false;
                    }
                }else {
                    if (balance[index-1] == '(') {
                        balance[index] = ' ';
                        index--;
                    }else {
                        return false;
                    }
                }

            }

        }
        return index == 0;
    }

    public static void main(String[] args) {
        Brackets brackets = new Brackets();
        System.out.println(brackets.chek("(()[([][]())[()][()][][]])([])()"));
    }
}
