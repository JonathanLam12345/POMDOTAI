package com.example.ai.jlam.pomdotai;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class POMBot {

    Random rand = new Random();


    //
    public static boolean wantRecommendations = false;

    public static boolean clickableList = false;

    //Command tags
    public static boolean goToProfile = false;
    public static boolean goToChat = false;


    //public static List<Event> eventList;
    RowItem RowItem_events;

    UserInfoDatabase userInfoDatabase;

    public POMBot(Context c) {
        userInfoDatabase = new UserInfoDatabase(c.getApplicationContext());

    }

    //get user message
    //return bot reply
    public String getBotMessage(String s) {
        s = " " + s.trim().toLowerCase() + " ";
        String say = s.trim();

        String reply = "";


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (s.contains(" repeat intro ")) {
            DiscoveryFragment.botIntro();
            return null;
        } else if (s.contains(" stupid ") || s.contains(" fuck ") || s.contains(" shit ") || s.contains(" cunt ") || s.contains(" ass ") || s.contains(" tits ") || s.contains(" testicles ") || s.contains(" bitch ") || s.contains(" nigga ") || s.contains(" nigger ") || s.contains(" fag ") || s.contains(" faggot ") || s.contains(" shitty ") || s.contains(" bastard ") || s.contains(" asshole ") || s.contains(" dick ") || s.contains(" penis ") || s.contains(" jackass ") || s.contains(" son of a bitch ") || s.contains(" pussy ") || s.contains(" blackie ") || s.contains(" gook ") || s.contains(" asshole ") || s.contains(" chink ") || s.contains(" paki ") || s.contains(" dipshit ") || s.contains(" whore ") || s.contains(" hoe ") || s.contains(" slut ") || s.contains(" bollocks ")) {
            String[] replies = {userInfoDatabase.getFirst_name() + " that's not very nice!!", "How rude!!!", "I don't like what you've just said."};
            reply = replies[rand.nextInt(replies.length)];
        } else if (s.trim().split(" ")[0].equals("say")) {
            return say.substring(say.indexOf(' '), say.length()).trim();
//            } else if (s.contains(" weather ") || s.contains(" temperature ")) {
//                BotAccessWeb weather = new BotAccessWeb();
//                weather.GetCurrentWeather();
//                return null;
        } else if (s.contains(" tell ") && s.contains(" joke ")) {
            String[] replies = {"Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.",
                    "I told my sister she drew her eyebrows too high. She seemed surprised.",
                    "My friend says to me: \"What rhymes with orange?\" I said: \"No it doesn't\"",
                    "A blind man walks into a bar... and a table... and a chair.",
                    "What did Michael Jackson call his denim store? Billie Jeans",
                    "What do dinosaur use to pay bills? Tyrannosaurus Checks",
                    "What do you call ghost's poop? Boo Boo.",
                    "Why did the banana go to the doctor? Because he wasn't peeling well.",
                    "What do you call a funny mountain? ",
                    "What does a robot frog say? Rib-bot.",
                    "How did crazy Jon get through the forest? He took the psycho-path.",
                    "What do you get when you cross a cow and a duck? Milk and quackers.",
                    "Did you know I used to be a banker? Ya... I lost interest.",
                    "Who does a pharaoh talk to when he's sad? His mummy.",
                    "What does a peanut say when it sneezes? Cashew!!!",
                    "What's orange and sounds like a parrot? A carrot.",
                    "My dentist is a really mean guy. He always hurts my fillings.",
                    "What did 50 cent say to his grandma after she gave him a sweater? G-U-Knit?",
                    "What do you call it when two chips fall in love? A relation-dip.",
                    "Which state has the smallest drinks? Mini-soda.",
                    "How did the whale defend itself? With a swordfish.",
                    "Why couldn't the toilet paper cross the road? He got stuck in a crack.",
                    "Did you hear about the lumberjack who got fired for cutting down too many trees? He saw too much.",
                    "Why aren't Koalas actual bears? They don't meet the Koalafications.",
                    "I would do a steak joke, but they're never well done.",
                    "What did the janitor say when he jumped out of the closet? SUPPLIES!!!",
                    "Who is the best kung fu vegetable? Brocc Lee",
                    "Why did the smartphone need glasses? It lost all its contacts.",
                    "When is the best day to cook? Fry-day.",
                    "What is the best tool to make a car move? A screwdriver.",
                    "What did aunt Ingrid say when she ran out of pancakes? How waffle!!!",
                    "Why is a pig's tail like waking up at 5am? It's twirly",
                    "What do you like a strawberry that likes to spin? A Berry-Go-Round.",
                    "My friend lisa recently got her real esatate license... Now she's my homegirl.",
                    "When does a sandwich cook? When it's bakin' lettuce and tomato."
            };
            reply = replies[rand.nextInt(replies.length)];
        }
        //What is my name?
        else if ((s.contains(" my ") && s.contains(" name ")|| (s.contains(" my ") &&s.contains(" name? ") ))) {
            reply = "Your name is: " + userInfoDatabase.getFirst_name() + " " + userInfoDatabase.getLast_name() + ".";
        }
        //What is your name?
        else if (s.contains(" your ") && s.contains(" name ")) {
            reply = "My name is POM.";
        }
        //How are you?
        else if ((s.contains(" how ") && s.contains(" are ") && s.contains(" you "))) {
            reply = "I'm doing great!!";
        }
        //What is my user ID?
        else if (s.contains(" my ") && s.contains(" user ") && s.contains(" id ")) {
            reply = "Your user ID is: " + userInfoDatabase.getUser_ID() + ".";
        }

        //What is my email??
        else if (s.contains(" my ") && s.contains(" email ")) {
            reply = "Your name is: " + userInfoDatabase.getEmail();
        }
        //Can you recommend me some events for today?
        else if (s.contains(" recommend ") || s.contains(" recommendations ") || s.contains(" recommendation ") || s.contains(" recommending ")) {
            reply = "Would you like a list of recommendations?";
            POMBot.wantRecommendations = true;
        }
        //Okay.
        else if (s.contains(" ok ") || s.contains(" okay ")) {
            reply = "Okay";
        } else if (s.contains(" thanks ") || s.contains(" thank you ")) {
            reply = "You're welcome.";
        }
        //hello.
        else if (s.contains(" hi ") || s.contains(" hello ") || s.contains(" hey ") || s.contains(" yo ")) {
            String[] replies = {"Hi there " + userInfoDatabase.getFirst_name(), "Hey. :)", "Greetings " + userInfoDatabase.getFirst_name()};
            reply = replies[rand.nextInt(replies.length)];
        } else if (s.contains(" profile ")) {
            POMBot.goToProfile = true;//
            reply = "Going to Profile page.";
        } else if (s.contains(" chat ")) {
            POMBot.goToChat = true;
            reply = "Going to Chat page.";
        } else {
            String[] replies = {"tell you a joke.", "give you a list of event recommendations for today.", "tell you the current temperature.", "say anything."};
            String reply2 = replies[rand.nextInt(replies.length)];
            reply = "Sorry, I'm not sure what you've just said. You can ask me to: " +
                    "\n • Tell you a joke.\n" +
                    "\n • Give you a list of event recommendations for today.\n" +
                    "\n • Tell you the current temperature.\n" +
                    "\n • Say anything.\n";
        }

        return reply;
    }
}