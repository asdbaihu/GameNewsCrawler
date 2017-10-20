package tv.duojiao.utils.spider;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
public class JsonUtils {
    public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
}
