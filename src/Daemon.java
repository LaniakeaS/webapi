import java.util.HashMap;
import java.util.Map;

public class Daemon extends Thread {

    protected static Map<String, User> users;
    private static Daemon daemon;


    static {

        users = new HashMap<>();

    }


    public static Daemon getInstance() {

        if (daemon == null)
            daemon = new Daemon();

        return daemon;

    }


    @Override
    public void run() {

        while (true) {}

    }

}
