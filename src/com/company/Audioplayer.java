
package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Audioplayer {
    public static void playSong(String songname) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner scan = new Scanner(System.in);
        String filepath = "src/com/company/songs/"+songname+".wav";
        File file = new File(filepath);
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        String response = "";

        while (!response.equals("Q")){
            System.out.println("P=Play, S=Pause, R=Reset, Q=Quit");
            response = scan.next();
            response=response.toUpperCase();

            switch (response){
                case ("P"): clip.start();
                    break;
                case ("S"): clip.stop();
                    break;
                case ("R"): clip.setMicrosecondPosition(0);
                    break;
                case ("Q"): clip.close();
                    break;
                default:
                    System.out.println("Not a valid response");
            }
        }
        System.out.println("Over");
    }

    public static void playPodcast(String podcastname) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner scan = new Scanner(System.in);
        String filepath = "src/com/company/songs/"+podcastname+".wav";
        File file = new File(filepath);
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        String response = "";

        while (!response.equals("Q")){
            System.out.println("P=Play, S=Pause, R=Reset, Q=Quit");
            response = scan.next();
            response=response.toUpperCase();

            switch (response){
                case ("P"): clip.start();
                    break;
                case ("S"): clip.stop();
                    break;
                case ("R"): clip.setMicrosecondPosition(0);
                    break;
                case ("Q"): clip.close();
                    break;
                default:
                    System.out.println("Not a valid response");
            }
        }
        System.out.println("Over");
    }
}