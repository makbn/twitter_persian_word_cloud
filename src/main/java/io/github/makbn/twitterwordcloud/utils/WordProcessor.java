package io.github.makbn.twitterwordcloud.utils;

import com.opencsv.CSVReader;
import io.github.makbn.twitterwordcloud.TWC;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

public class WordProcessor {

    public static File generateText(HashMap<String, String> argsMap) throws IOException {
        File dir = new File(argsMap.get("-p"));
        File csv = dir.listFiles()[0];
        File accT = new File(dir.getPath(), "accT.txt");
        accT.createNewFile();
        FileWriter fw = new FileWriter(accT);
        FileReader fr = new FileReader(csv);
        CSVReader scvr = new CSVReader(fr);

        Iterator<String[]> it = scvr.iterator();
        //ignore first line
        if(it.hasNext())
            it.next();

        while (it.hasNext()){
            String[] tweet = it.next();
            String result = tweet[2];

            System.out.println(tweet[2]);
            if(!result.startsWith("https://twitter.com"))
                fw.write(result+"\n");
        }

        fw.flush();
        fw.close();
        fr.close();
        scvr.close();

        return accT;
    }

    static Collection<String> loadStopWords() throws FileNotFoundException {
        HashSet<String> stopwords = new HashSet<>();
        String swDir = TWC.class.getClassLoader().getResource("stopwords").getFile();
        File dir = new File(swDir);
        BufferedReader reader;
        for (File swf : dir.listFiles()){
            reader  = new BufferedReader(new FileReader(swf));
            stopwords.addAll(reader.lines()
                    .map(s -> s.trim()) // substitute with your deilimeter
                    .collect(Collectors.toList()));
        }

        return stopwords;
    }

}
