import com.company.Main;
import org.junit.Test;

import java.util.Scanner;

public class TestBytes {

    @Test
    public void firstTestMethodFindOfDifferentTracks() throws Exception {
        String input = "1010 0010";
        Main.findOfDifferentTracks(new Scanner(input));
    }

    @Test
    public void secondTestMethodFindOfDifferentTracks() throws Exception {
        String input = "00111 11111";
        Main.findOfDifferentTracks(new Scanner(input));
    }
}
