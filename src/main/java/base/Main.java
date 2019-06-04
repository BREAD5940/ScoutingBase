package base;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cpjd.main.TBA;
import com.cpjd.models.events.Award;
import com.cpjd.models.teams.Team;

public class Main{
    public static TBA tbaApi;
    public static Session sesh = new Session(2019, "Sac 2019", "2019cada", "sac_2019/", new Color(100, 199, 254));

    public static void main(String[] args) throws Exception{
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();

    }


}