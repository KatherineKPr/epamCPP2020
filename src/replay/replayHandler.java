package replay;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;


public class replayHandler {

    JSONArray listArrange = new JSONArray();
    JSONArray listScore = new JSONArray();

    public void writeInList(String crystalArrangement, String score) {
        JSONObject obj = new JSONObject();
        listScore.add(score);
        obj.put("Score:", listScore);
        listArrange.add(crystalArrangement);
        obj.put("Arrangement:", listArrange);
        try (FileWriter file = new FileWriter("src\\replay\\test.json")) {

            file.write(obj.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


