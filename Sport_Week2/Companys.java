import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Companys {
    int count;
    List<List<String>> comp = new ArrayList<>();
    String[] arr;
    boolean[] used;


    public Companys(String path) throws IOException {
        String context = readFile(path, Charset.defaultCharset());
        int idx = context.indexOf('\r');
        count = Integer.parseInt(context.substring(0, idx));
        context = context.substring(idx + 2);
        arr = context.split("\r\n");
        used = new boolean[count];
    }

    void process() {
        boolean change = true;

        //while (change) {
            //change = false;
            List<String> list = new ArrayList<>();
            for(int i = 0; i < count; i++) {
                System.out.print(i);
                //if(!used[i]) {
                    if (!list.contains(arr[i])) {
                        change = true;
                        list.add(arr[i]);
                        //used[i] = true;
                    }else {
                        comp.add(list);
                        System.out.print("adding");
                        list = new ArrayList<>();
                        list.add(arr[i]);
                        //used[i] = true;
                    }
                //}
                System.out.println();
            }
            if (!list.isEmpty()){
                comp.add(list);
            }

        //}
        print();
    }

    void print() {
        for(List<String> list : comp) {
            System.out.println(list);
        }
        System.out.println(comp.size());
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) {
        try {
            Companys companys = new Companys("C:\\Users\\User\\parserxml\\src\\main\\resources\\ice2.txt");
            companys.process();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
