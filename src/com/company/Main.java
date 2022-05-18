package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Main {
    //int user_Id=0;
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Scanner scan = new Scanner(System.in);
        String songname = "";
        String podcastname = "";

        System.out.println("Welcome to JukeBox Music Player");
        System.out.println();
        do {
            System.out.println("1.Login or 2.SignUp or 3.Exit");
            int choice = scan.nextInt();
            switch (choice) {

                case 1:
                    System.out.println("\n1.UserName \n2.Password");
                    scan.nextLine();
                    String username = scan.nextLine();
                    String pwd = scan.nextLine();
                    if (Main.userValid(username, pwd)) {
                        System.out.println("LoggedIn Successfully");
                        System.out.println();
                        do {
                            System.out.println("What you wish to do \n1.Play Song \n2.Play Podcast \n3.Create a new playlist \n4.Play from your Playlist \n5.Exit");
                            int list = scan.nextInt();
                            switch (list) {

                                case 1:
                                    System.out.println("Songs List");
                                    Main.songList();
                                    System.out.println("Enter the song name u wish to play");
                                    songname = scan.next();
                                    Audioplayer.playSong(songname);
                                    break;
                                case 2:
                                    System.out.println("Podcast List");
                                    Main.podcastList();
                                    System.out.println("Enter the podcast name u wish to play");
                                    podcastname = scan.next();
                                    Audioplayer.playPodcast(podcastname);
                                    break;
                                case 3:
                                    System.out.println("Enter your PlaylistName and UserId");
                                    String playlistName = scan.next();
                                    //scan.next();
                                    int userId = scan.nextInt();
                                    Main.createPlaylist(playlistName, userId);
                                    break;
                                case 4:
                                    System.out.println("These are the Playlist details:");
                                    System.out.println();
                                    Main.showPlaylistDetails();
                                    System.out.println();
                                    System.out.println("Enter your PlaylistId");
                                    int playListId = scan.nextInt();
                                    Main.viewPlaylist(playListId);
                                    System.out.println();
                                    System.out.println("Enter the SongName or PodcastName you want to play");
                                    songname = scan.next();
                                    Audioplayer.playSong(songname);
                                    break;
                                case 5:
                                    return;
                            }
                        } while (true);
                    }
                    else
                    {
                        System.out.println("Invalid UserName or Password");
                    }
                    break;
                case 2:
                    System.out.println("Enter your details in order \n1.Name \n2.Email \n3.Contact \n4.Password");
                    scan.nextLine();
                    String name = scan.nextLine();
                    String email = scan.nextLine();
                    long contact = scan.nextLong();
                    scan.nextLine();
                    String psd = scan.nextLine();
                    Main.userSignup(name, email, contact, psd);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }while (true);
    }
    public static boolean userValid(String username,String pwd)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            PreparedStatement st = con.prepareStatement("select user_name,user_pwd from userdetails where user_name=? and user_pwd=?");
            st.setString(1,username);
            st.setString(2,pwd);
            ResultSet r = st.executeQuery();
            if (r.next()==false)
            {
                return false;
            }
            else
                return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void userSignup(String UserName,String Emailid,long Contact,String password)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            PreparedStatement st1 = con.prepareStatement("insert into userdetails (user_name,user_email,contact,user_pwd) values(?,?,?,?)");
            st1.setString(1,UserName);
            st1.setString(2,Emailid);
            st1.setLong(3,Contact);
            st1.setString(4,password);

            int i = st1.executeUpdate();
            if (i>0)
            {
                System.out.println("User created Successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void songList()
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from song");
            while (rs.next())
            {
                System.out.format("Song Id: %d, SongName: %s, Duration %s, ArtistId %d",rs.getInt(1),rs.getString(2),
                        rs.getTime(3),rs.getInt(4));
                System.out.println();
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public static void podcastList()
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from podcast");
            while (rs.next())
            {
                System.out.format("Podcast Id: %d, PodcastName %s, Duration %s, Nos of Episode %d, GenreId %d",rs.getInt(1),
                        rs.getString(2),rs.getTime(3),rs.getInt(4),rs.getInt(5));
                System.out.println();
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showPlaylistDetails()
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from playlist");
            while (rs.next())
            {
                System.out.format("Playlist Id: %d, PlaylistName: %s, PlaylistCreatedDate %s, UserId %d",rs.getInt(1),
                        rs.getString(2),rs.getTime(3),rs.getInt(4));
                System.out.println();
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createPlaylist(String playlistName, int userId)
    {
        Scanner scan = new Scanner(System.in);
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            PreparedStatement st = con.prepareStatement("insert into playlist (playlist_name,user_id) values(?,?)");
            st.setString(1,playlistName);
            //scan.nextLine();
            st.setInt(2,userId);
            int i = st.executeUpdate();
            if (i>0)
            {
                System.out.println("Successfully created Playlist");
            }
            System.out.println("Insert Songs or podcast in playlist");
            String input = scan.next();

            if (input.equalsIgnoreCase("Songs")) {
                Main.songList();
                System.out.println("Enter the song Id you wish to add");
                int songId = scan.nextInt();
                PreparedStatement st2 = con.prepareStatement("select * from song where song_id = ?");
                st2.setInt(1,songId);
                ResultSet rs = st2.executeQuery();
                Main.showPlaylistDetails();
                System.out.println("Enter your playlistId");
                int playlistId = scan.nextInt();
                rs.next();
                String songpl_name = rs.getString(2);
                int playlist_id = playlistId;
                int song_id = songId;
                PreparedStatement st1 = con.prepareStatement("Insert into songplaylist (song_name,playlist_id,song_id) values(?,?,?)");
                st1.setString(1, songpl_name);
                scan.nextLine();
                st1.setInt(2, playlist_id);
                st1.setInt(3, song_id);
                int j = st1.executeUpdate();
                if (j>0)
                {
                    System.out.println("Song " +songpl_name+ " is successfully added to the palylist");
                    //System.out.println("Do you wish to add more songs to the playlist yes/no");
                    //scan.nextLine();
                    //System.out.println("Enter the song Id you wish to add");
                }
            }

            if (input.equalsIgnoreCase("podcast"))
            {
                Main.podcastList();
                System.out.println("Enter the PodcastID you wish to add");
                int podcastId = scan.nextInt();
                PreparedStatement st3 = con.prepareStatement("Select * from podcast where podcast_id = ?");
                st3.setInt(1,podcastId);
                ResultSet rs = st3.executeQuery();
                Main.showPlaylistDetails();
                System.out.println("Enter your playlistId");
                int playlistId = scan.nextInt();
                rs.next();
                String podcastpl_name = rs.getString(2);
                int playlist_Id = playlistId;
                int podcast_id = podcastId;
                PreparedStatement st4 = con.prepareStatement("Insert into podcastplaylist (podcast_name,playlist_id,podcast_id) values(?,?,?)");
                st4.setString(1,podcastpl_name);
                scan.nextLine();
                st4.setInt(2,playlist_Id);
                st4.setInt(3,podcast_id);
                int k = st4.executeUpdate();
                if (k>0)
                {
                    System.out.println("Podcast is successfully added to playlist");
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void viewPlaylist(int playlistId)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","password@123");
            PreparedStatement st = con.prepareStatement("select * from playlist where playlist_id = ?");
            st.setInt(1,playlistId);
            ResultSet rs = st.executeQuery();
            rs.next();
            System.out.println("List of song or podcast in the playlist");
            PreparedStatement st1 = con.prepareStatement("select * from songplaylist where playlist_id = ?");
            st1.setInt(1,playlistId);
            ResultSet rs1 = st1.executeQuery();
            while (rs1.next())
            {
                System.out.format("SongplayListId: %d, SongName: %s",rs1.getInt(1),rs1.getString(2));
                System.out.println();
            }
            PreparedStatement st2 = con.prepareStatement("select * from podcastplaylist where playlist_id = ?");
            st2.setInt(1,playlistId);
            ResultSet rs2 = st2.executeQuery();
            while (rs2.next())
            {
                System.out.format("PodcastPlayListId: %d, PodcastName: %s",rs2.getInt(1),rs2.getString(2));
                System.out.println();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
