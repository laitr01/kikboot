/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kikboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author trach
 */
public class KikBoot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String messages[][] = {{"Sale today!","2837273"}, 
//                                {"Unique offer!","3873827"}, 
//                                {"Only today and only for you!","2837273"}, 
//                                {"Sale today!","2837273"}, 
//                                {"Unique offer!","3873827"}};
//        String spamSignals[] = {"sale", 
//                                "discount", 
//                                "offer"};
        String messages[][] ={{"Check Codefights out","7284736"}, 
                            {"Check Codefights out","7462832"}, 
                            {"Check Codefights out","3625374"}, 
                            {"Check Codefights out","7264762"}};
        String spamSignals[] = {"sale", 
                                "discount", 
                                "offer"};
        String[] result = spamDetection(messages, spamSignals);
        for (String string : result) {
            System.out.println(string + "\n");
        }
    }
        static String[] result;
    static String[] spamDetection(String[][] messages, String[] spamSignals) {
        result = new String[4];
        checkSameContents = new HashMap<>();
        checkTotalSameContents = new HashMap<>();
        int totalOfMessage = messages.length;
        
        long cntWords =0;
        for(int i = 0; i < totalOfMessage; i++){
            String mess = messages[i][0];
            //checkSameContent( mess,messages[i][1]);
            //1----
            
            if(mess.split(" ").length < 5){
                cntWords++;
            }
            //2----
            if(!checkSameContents.containsKey(messages[i][1])){
                HashMap<String, Integer> arr = new HashMap<>();
                arr.put(mess,1);
                checkSameContents.put(messages[i][1], arr);
            }else{
                HashMap<String, Integer> arr = checkSameContents.get(messages[i][1]);
                if(arr.containsKey(mess)){
                    int value = arr.get(mess);
                    arr.put(mess, ++value);
                }else{
                    arr.put(mess, 1);
                }
                checkSameContents.put(messages[i][1], arr);
            }
            //3----
            if(checkTotalSameContents.containsKey(mess)){
                int value = checkTotalSameContents.get(mess);
                checkTotalSameContents.put(mess, ++value);
            }else{
                checkTotalSameContents.put(mess, 1);
            }
        }
        checkMoreThan5Words(cntWords, totalOfMessage);
        checkSameContent(checkSameContents);
        checkTotalSameContent(totalOfMessage);
        checkWordSpam(spamSignals, totalOfMessage);
        return result;
    }
    static HashMap<String, HashMap<String, Integer>> checkSameContents;
    
    static void checkSameContent(HashMap<String, HashMap<String, Integer>> checkSameContents){
        
        String err ="failed:";
        String str ="";
        boolean flag = false;
        for(Iterator iterator = checkSameContents.keySet().iterator();
                iterator.hasNext();){
            String key = iterator.next().toString();
            HashMap<String, Integer> arr = checkSameContents.get(key);
            int total = arr.size();
            int max = 0,temp, temp2 = 0;
//            String tempArr[] = new String[total]; 
//            arr.toArray(tempArr);
//            for (int i = 0;i < total-1; i++) {
//                if(tempArr[i].equals(tempArr[i+1])){
//                    temp++;
//                }else{
//                    if(temp > temp2){
//                        temp2 = temp;
//                        temp =1;
//                    }  
//                }
//            }
//            if(temp2/total>0.5) {
//                err+=" "+key;
//                flag = true;
//            }
            for(Iterator it = arr.keySet().iterator(); it.hasNext();){
                String mess = (String) it.next();
                temp = arr.get(mess);
                if(temp > max){
                    max = temp;
                }
            }
            if((float)max/total>0.5 && max>=2) {
                str=" "+key + str;
                flag = true;
            }
        }
        if(!flag) result[1] = "passed"; else result[1] = err+str;
    }
    static void checkMoreThan5Words(long cntWords, int totalOfMessage){
        if(cntWords > totalOfMessage * 0.9){
            result[0] = "failed: "+ratio(cntWords, totalOfMessage);
        }else{
            result[0] = "passed";
        }
    }
    static String ratio(long cntWords, int totalOfMessage){
        if(cntWords/totalOfMessage<1){
            return cntWords+"/"+totalOfMessage;
        }
        return (cntWords/totalOfMessage)+"/"+(totalOfMessage/totalOfMessage);
    }
    static HashMap<String, Integer> checkTotalSameContents;
    static void checkTotalSameContent(int totalMess) {
        boolean flag = false;
        String err ="failed:";
        for(Iterator it = checkTotalSameContents.keySet().iterator(); it.hasNext();){
            int temp, max =0;
            
            String mess = (String) it.next();
            temp = checkTotalSameContents.get(mess);
            if((float)temp/totalMess>0.5 && totalMess>=2) {
                err+=" "+mess;
                flag = true;
            }
        }
        if(!flag) result[2] = "passed"; else result[2] = err;
    }

    static void checkWordSpam(String[] spamSignals, int total) {
         boolean flag = false;
        String err ="failed:";
        int sinCnt = spamSignals.length;
        int cntSpam = 0, temp;
        for(Iterator it = checkTotalSameContents.keySet().iterator(); it.hasNext();){
            String mess = (String) it.next();
            temp = checkTotalSameContents.get(mess);
            for (int i = 0; i < spamSignals.length; i++) {
                String spamSignal = spamSignals[i];
                if(mess.toLowerCase().contains(spamSignal.toLowerCase())){
                    cntSpam+=temp;
                    if(!err.contains(spamSignal))
                        err+= " "+ spamSignal;
                    break;
                }                
            }                    
        }
        if((float)cntSpam/total>0.5){                
            flag = true;
        } 

        if(!flag) result[3] = "passed"; else result[3] = err;
    }

}
