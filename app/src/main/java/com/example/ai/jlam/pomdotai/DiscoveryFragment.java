package com.example.ai.jlam.pomdotai;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import static android.app.Activity.RESULT_OK;


public class DiscoveryFragment extends AppCompatActivity implements TextToSpeech.OnInitListener {

    UserInfoDatabase userInfoDatabase;

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
    EditText editText_input;
    private static ArrayList<RowItem> arrayList;
    private static MyCustomAdapter mAdapter;
    private static ListView listView_list;
    private ImageView imageView_send_button;
    private ImageView imageView_speak;


    static String message; // What the user says
    static String botReply; //What the bot says


    private Context context;
    private static POMBot bot;
    public static TextToSpeech tts;
    static String[] messageArray = new String[4];

    static String soceroID;
    static String fullName;
    static String email;

    static String greetingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_fragment);
        Log.d("bot :", "onCreateView");


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        userInfoDatabase = new UserInfoDatabase(this);

        soceroID = userInfoDatabase.getSocero_ID();
        fullName = userInfoDatabase.getFirst_name() + " " + userInfoDatabase.getLast_name();
        email = userInfoDatabase.getEmail();
        greetingTime = timeOfTheDay();

    }

    public static String timeOfTheDay() {
        final Calendar c = Calendar.getInstance();
/*
* 6:00am to 11:59am - morning
* 12:00pm to 17:59 - afternoon
* 18:01 to 5:59 - evening*/
        if (c.get(Calendar.HOUR_OF_DAY) >= 6 && c.get(Calendar.HOUR_OF_DAY) <= 11) {
            return "Good morning!";
        } else if (c.get(Calendar.HOUR_OF_DAY) >= 12 && c.get(Calendar.HOUR_OF_DAY) <= 17) {
            return "Good afternoon!";
        } else {
            return "Good evening!";
        }
    }


    public static void botIntro() {
        clearList();
        speak(greetingTime + "My name is POM. Your Personal Opportunity Manager. Would you like some event recommendations for today?");
        updateList(new RowItem("POM", greetingTime + " My name is POM. Your Personal Opportunity Manager. Would you like some event recommendations for today?"));
        POMBot.wantRecommendations = true;
    }

    public static void speak(String s) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tts.setPitch((float) 1.0);
                tts.setSpeechRate((float) 1.0);
                tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("bot :", "onViewCreated");
        this.context = getContext();
        super.onViewCreated(view, savedInstanceState);

        bot = new POMBot(context, baroServiceProvider);
        editText_input = view.findViewById(R.id.editText_input);
        listView_list = view.findViewById(R.id.list);
        listView_list.setClickable(true);

        imageView_send_button = view.findViewById(R.id.send_button);
        imageView_speak = view.findViewById(R.id.btSpeak);
        tts = new TextToSpeech(getContext(), this);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        checkVoiceRecognition();

        arrayList = new ArrayList<RowItem>();

        mAdapter = new MyCustomAdapter(context, arrayList);
        listView_list.setAdapter(mAdapter);
        botIntro();


        listView_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (POMBot.clickableList) {

                }
            }
        });

        imageView_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POMBot.clickableList = false;
                Log.d("bot :", "mic clicked");

                //Don't clear the listview when the POM is the one asking the question.
                if (DiscoveryFragment.botReply.contains("recommendations") && DiscoveryFragment.botReply.contains("Would")) {
                    Log.d("DiscoveryFragment", "Don't clear!");
                } else {
                    Log.d("DiscoveryFragment", "Clear: " + DiscoveryFragment.message + " " + DiscoveryFragment.botReply);
                    clearList();
                }
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
            }
        });


        //User click on the send button
        imageView_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_input.getText().toString().equals("")) {
                    POMBot.clickableList = false;
                    Log.d("bot :", "imageView clicked");

                    //Don't clear the listview when the POM is the one asking the question.
                    if (DiscoveryFragment.botReply.contains("recommendations") && DiscoveryFragment.botReply.contains("Would")) {
                        Log.d("DiscoveryFragment", "Don't clear!");
                    } else {
                        Log.d("DiscoveryFragment", "Clear: " + DiscoveryFragment.message + " " + DiscoveryFragment.botReply);
                        clearList();
                    }
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    DiscoveryFragment.message = editText_input.getText().toString();
                    Log.d("bot :", "sending message: " + message);


                    updateList(new RowItem("user", DiscoveryFragment.message));

                    editText_input.setText("");

                    sendMessageToBot(message);

                }
            }
        });
    }


    //User spoke a message.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            // If Voice recognition is successful then it returns RESULT_OK
            if (resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    //if bot is asking if user
//                   smoo
                    DiscoveryFragment.message = textMatchList.get(0);//////////////////

                    RowItem RowItemuser = new RowItem("user", DiscoveryFragment.message);

                    arrayList.add(RowItemuser);

                    // refresh the list
                    mAdapter.notifyDataSetChanged();

                    // Result code for various error.
                    if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
                        showToastMessage("Audio Error");
                    } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
                        showToastMessage("Client Error");
                    } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                        showToastMessage("Network Error");
                    } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                        showToastMessage("No Match");
                    } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                        showToastMessage("Server Error");
                    }

                    sendMessageToBot(message);
                }
                // Result code for various error.
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static void updateList(RowItem s) {
        // NOT (type user) , NOT (type POM and user) <--recommendations, NOT (POM intro)

        DiscoveryFragment.botReply = s.getMessage();

        //sending...
        if (s.getImageType().equals("POM") && !s.getMessage().contains("My name is POM. Your Personal Opportunity Manager. Would you like some event recommendations for today?")) {
            Log.d("DiscoveryFragment", "MESSAGE: " + DiscoveryFragment.message + " BOT REPLY: " + DiscoveryFragment.botReply);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    postData();
                }
            });
            t.start();
        } else {
            Log.d("DiscoveryFragment", "Won't keep track of recommendations/POM intro.");
        }

        arrayList.add(s);
        mAdapter.notifyDataSetChanged();
        listView_list.smoothScrollToPosition(0);
    }

    public static void postData() {
        //message and bot reply should be the most updated.

        Log.d("post data", "");
        String fullUrl = "https://docs.google.com/forms/d/e/1FAIpQLSex_yAMaQ5RRylY5QlW_wM38SQwhE1cbRYlop0u3wHgmt5p1A/formResponse";
        HttpRequest mReq = new HttpRequest();

        String data = "entry.197557228=" + URLEncoder.encode(DiscoveryFragment.soceroID) + "&" +
                "entry.1749078208=" + URLEncoder.encode(DiscoveryFragment.fullName) + "&" +
                "entry.1269961482=" + URLEncoder.encode(DiscoveryFragment.email) + "&" +
                "entry.235803520=" + URLEncoder.encode(DiscoveryFragment.message) + "&" +
                "entry.687241897=" + URLEncoder.encode(DiscoveryFragment.botReply);

        mReq.sendPost(fullUrl, data);
    }


    //get message from user(by input text/speech) and process it with a bot response.
    public void sendMessageToBot(String message) {
        String botMessage = bot.getBotMessage(message);
        if (POMBot.goToProfile) {
            POMBot.goToProfile = false;
            Log.d("DiscoveryFragment: ", " Going to Profile page. (Not implemented yet)");


            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", userInfoDatabase.getUser_ID());
            bundle.putString("YOUR_USER_ID", userInfoDatabase.getUser_ID());

            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame, profileFragment).addToBackStack(null).commit();
            getActivity().getSupportFragmentManager().executePendingTransactions();
        } else if (POMBot.goToChat) {
            POMBot.goToChat = false;
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame, new Chat()).addToBackStack(null).commit();
            getActivity().getSupportFragmentManager().executePendingTransactions();
        }


        if (botMessage != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speak(botMessage);
                    updateList(new RowItem("POM", botMessage));
                }
            }, 1500);
        }
    }


    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            imageView_speak.setEnabled(false);
            Toast.makeText(context, "Voice recognizer not present", Toast.LENGTH_SHORT).show();
        }
    }


    void showToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void clearList() {
        arrayList.clear();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale bahasa = tts.getLanguage();
            int result = tts.setLanguage(bahasa);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS: ", "This language is not supported!!");
            } else {
                // Do nothing
            }
        } else {
            Log.e("TTS: ", "Initialization failed!!");
        }
    }
}