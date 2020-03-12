package io.github.makbn.twitterwordcloud.utils;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import io.github.makbn.twitterwordcloud.TWC;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TWordCloud {

    public static void generateTWC(File text) throws IOException, FontFormatException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(250);
        frequencyAnalyzer.setMinWordLength(3);
        frequencyAnalyzer.setStopWords(WordProcessor.loadStopWords());
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(text);

        final Dimension dimension = new Dimension(600, 600);
        final com.kennycason.kumo.WordCloud wordCloud = new com.kennycason.kumo.WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(5);

        wordCloud.setBackground(new CircleBackground(300));

        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.decode("#4978BC"), Color.decode("#002E5E"), Color.decode("#001D38"), 25, 25));
        wordCloud.setBackgroundColor(Color.decode("#FFCC00"));
        wordCloud.setKumoFont(loadFont());
        wordCloud.setFontScalar(new SqrtFontScalar(10, 55));
        wordCloud.build(wordFrequencies);

        wordCloud.writeToFile("result/done.png");
    }

    private static KumoFont loadFont() throws IOException, FontFormatException {
        String fontFile = TWC.class.getClassLoader().getResource("fonts/Tanha.ttf").getFile();
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile));
        return new KumoFont(font);
    }

}
