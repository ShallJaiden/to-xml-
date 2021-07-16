package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {

    Set<IP_Port_Pair> set = new LinkedHashSet<>();
    Map<String, IP_Port_Pair> serverMap = new LinkedHashMap<>();
    Map<IP_Port_Pair, Integer> serverIndexMap =  new LinkedHashMap<>();
    Map<String, Integer> capacityMap = new LinkedHashMap<>();
    Stack<IP_Port_Pair> serverStack = new Stack<>();
    Map<String, String> dnsMap = new HashMap<>();
    Map<String, String> validation = new HashMap<>();
    int count = 1;

    public static void main(String[] args) {

        Main main = new Main();
        main.fileHashing();
        main.DNS();
        main.toXml();
        main.toXmlBeta();

    }

    private void fileHashing() {
        StringBuilder contentBuilder = getStringBuilder("D:\\shitloads\\src\\demo.txt");
        String content =  contentBuilder.toString();


        String[] str = content.split("\n");


        for (String s : str ){
            String[] aLine = s.split("\\s+");
            IP_Port_Pair pair = new IP_Port_Pair(aLine[1], aLine[2]);
            Current_Max_Pair capPair = new Current_Max_Pair(aLine[3], aLine[4]);
            int capacity = calculateCapacity(capPair);
            capacityMap.put(aLine[0],capacity);
            set.add(pair);
            serverStack.addAll(set);
            serverMap.put(aLine[0], serverStack.pop());
            serverStack.clear();

        }

        for (IP_Port_Pair pair : set) {
            serverIndexMap.put(pair, count);
            count++;
        }

    }

    public void DNS() {
        StringBuilder contentBuilder = getStringBuilder("D:\\shitloads\\src\\DNS.txt");
        String content =  contentBuilder.toString();


        String[] str = content.split("\n");



        for (String s : str){
            String[] aLine = s.split("\\s+");
            dnsMap.put(aLine[0],aLine[1]);
            validation.put(aLine[0],aLine[2]);



        }


    }

    private int calculateCapacity(Current_Max_Pair capPair) {
        double ratio = Double.parseDouble(capPair.currentUser) / Double.parseDouble(capPair.maxUser);
        if (ratio >=0 && ratio < 0.1) return 1;
        if (ratio >=0.1 && ratio < 0.3) return 2;
        if (ratio >=0.3 && ratio < 0.4) return 3;
        if (ratio >=0.4 && ratio < 0.5) return 4;
        if (ratio >=0.5 && ratio < 0.7) return 5;
        if (ratio >=0.7 && ratio < 0.8) return 6;
        else return 7;

    }

    private static StringBuilder getStringBuilder(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder;
    }

    public void toXml() {
        System.out.print("<root>\n");
        System.out.print("\t\t<servers>\n");
        set.forEach(i -> System.out.print("\t\t\t<server>" + i.ip + "/" + i.port + "</server>\n"));
        System.out.print("\t\t</servers>\n");
        System.out.print("\t\t<zones>\n");
        capacityMap.forEach((k, v) -> System.out.print("\t\t\t<zone name=“" + k + "” serverIndex=\"" + serverIndexMap.get(serverMap.get(k)) + "\" capacity=\"" + v + "\"/>\n"));
        System.out.print("\t\t</zones>\n");
        System.out.print("</root>\n");
    }

    public void toXmlBeta() {
        System.out.print("<root>\n");
        System.out.print("\t\t<servers>\n");
        for (IP_Port_Pair pair : set) {
            if (dnsMap.containsKey(pair.ip) && validation.get(pair.ip).equals("1") ) {
                System.out.print("\t\t\t<server>" + dnsMap.get(pair.ip) + "/" + pair.port + "</server>\n");
            }
            else
                System.out.print("\t\t\t<server>" + pair.ip + "/" + pair.port + "</server>\n");
        }
        System.out.print("\t\t</servers>\n");
        System.out.print("\t\t<zones>\n");
        capacityMap.forEach((k, v) -> System.out.print("\t\t\t<zone name=“" + k + "” serverIndex=\"" + serverIndexMap.get(serverMap.get(k)) + "\" capacity=\"" + v + "\"/>\n"));
        System.out.print("\t\t</zones>\n");
        System.out.print("</root>");
    }
}
