package io.github.makbn.twitterwordcloud;

import io.github.makbn.twitterwordcloud.utils.TWordCloud;
import io.github.makbn.twitterwordcloud.utils.WordProcessor;
import ir.ac.um.ce.projectnews.crawler.Crawler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TWC {
    private static HashMap<String, String> argsMap = new HashMap();

    public static void main(String[] args) throws IOException, FontFormatException {
        String[] vArgs = validateArgs(args);
        Crawler.main(vArgs);
        File text = WordProcessor.generateText(argsMap);
        TWordCloud.generateTWC(text);
    }

    private static String[] validateArgs(String[] args) {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-i":
                        argsMap.put("-i", args[i + 1]);
                        break;
                    case "-s":
                        argsMap.put("-s", args[i + 1]);
                        break;
                    case "-e":
                        argsMap.put("-e", args[i + 1]);
                        break;
                    case "-m":
                        argsMap.put("-m", args[i + 1]);
                        break;
                    case "-p":
                        argsMap.put("-p", args[i + 1]);
                        break;
                    case "-n":
                        argsMap.put("-n", args[i + 1]);
                        break;
                    default:
                        System.out.println("unknown arg: " + args[i]);
                        break;
                }
            }
        }

        if (!argsMap.containsKey("-i")) {
            Scanner s = new Scanner(System.in);
            String id = "";
            while (id.trim().isEmpty()) {
                System.out.println("Pleas enter your twitter id:\n");
                id = s.nextLine();
            }
            argsMap.put("-i", id);
        }

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


        if (!argsMap.containsKey("-s")) {
            Calendar prevYear = Calendar.getInstance();
            prevYear.add(Calendar.YEAR, -1);
            argsMap.put("-s", simpleDateFormat.format(prevYear.getTime()));

            argsMap.put("-e", simpleDateFormat.format(Calendar.getInstance().getTime()));
        }

        if (!argsMap.containsKey("-p")) {
            File dir = new File("result/");
            if (!dir.exists()) {
                dir.mkdir();
            } else {
                if(dir.listFiles() != null)
                    Arrays.stream(dir.listFiles())
                            .forEach(file -> file.delete());
            }
            argsMap.put("-p", "./result/");
        }


        String[] vArgs = new String[argsMap.size() * 2];
        int index = 0;
        for (Map.Entry<String, String> arg : argsMap.entrySet()) {
            vArgs[index] = arg.getKey();
            vArgs[index + 1] = arg.getValue();
            index += 2;
        }

        return vArgs;
    }
}
