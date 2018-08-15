package com.jjoey.sportseco.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.VideoView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.CalendarViewActivity;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.models.Event;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.SessionMeeting;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.CalendarConstants;
import com.jjoey.sportseco.utils.Constants;
import com.jjoey.sportseco.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jjoey.sportseco.utils.CalendarConstants.EVENT_TO_ADD;
import static com.jjoey.sportseco.utils.CalendarConstants.LIST_MEETINGS;

public class BatchFragment extends Fragment {

    private static final String TAG = BatchFragment.class.getSimpleName();

    private RecyclerView sessionsRV;
    private VideoView videoView;

    private String coachId = null, batchId = null,
                programId, programUserMapId;

    private List<Sessions> sessionsList = new ArrayList<>();
    private SessionsAdapter adapter;
    private Sessions sessions;
    private String videoURL;

    private Event event;
    private ArrayList<String> meetings;
    private String sessionName, programName, dateStart, dateEnd,
            hour_start, hour_end, min_start, min_end;

    public BatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coachId = getArguments().getString("coach_id");
        batchId = getArguments().getString("batch_id");

        event = (Event) getActivity().getIntent().getSerializableExtra(EVENT_TO_ADD);
        meetings = (ArrayList<String>) getActivity().getIntent().getSerializableExtra(LIST_MEETINGS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_batch, container, false);

        initViews(view);

        setUpSessionsRV();

        initVideoOfDay();

        return view;
    }

    private void initViews(View view) {
        sessionsRV = view.findViewById(R.id.sessionsRV);
        videoView = view.findViewById(R.id.videoView);
    }

    private void setUpSessionsRV() {
        sessionsRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        sessionsRV.setLayoutManager(llm);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity()
                , R.anim.layout_anim_fall_down);
        sessionsRV.setLayoutAnimation(controller);

        List<ProgramDetails> details = new Select()
                .from(ProgramDetails.class)
                .where("coach_id=?", coachId)
                .execute();

        // TODO: 7/11/2018 One Major flaw if coach has multiple programs, this might not work as expected
        for (int i = 0; i < details.size(); i++) {
            programId = details.get(i).getProgId();
            programUserMapId = details.get(i).getProgUserMapId();
//            if (getAllSessions().size() > 0){
//                // do nothing
//                Log.d(TAG, "From db");
//                sessionsList = getAllSessions();
//                adapter = new SessionsAdapter(getActivity(), sessionsList);
//                sessionsRV.setAdapter(adapter);
//            } else {
//                Log.d(TAG, "From network");
//            }
            fetchSessions(coachId, batchId, programId, programUserMapId);
        }
        Log.d(TAG, "Sessions DB size:\t" + getAllSessions().size());

    }

    private List<Sessions> getAllSessions() {
        return new Select()
                .from(Sessions.class)
                .orderBy("id ASC")
                .execute();
    }

    private void fetchSessions(String coachId, String batchId, String programId, String programUserMapId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
            jsonObject.put("batch_id", batchId);
            jsonObject.put("prg_id", programId);
            jsonObject.put("prg_user_map_id", programUserMapId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Constants.SESSION_LIST)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Sessions for Program")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
//                            Log.d(TAG, "Program Session List Response:\t" + response.toString());
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray array = object.getJSONArray("Session_details");
                                for (int m = 0; m < array.length(); m++) {
                                    JSONObject obj = array.getJSONObject(m);

                                    sessions = new Sessions();

                                    String programId = (obj.getString("prg_id"));
                                    sessionName = obj.getString("session_name");
                                    programName = obj.getString("prg_name");
                                    String programSessionId = (obj.getString("prg_session_id"));
                                    String videoLink = (obj.getString("prg_session_video_link"));
                                    String coverImage = obj.getString("prg_session_image");
                                    String desc = obj.getString("prg_session_description");
                                    String focusPoints = obj.getString("prg_session_focus_points");
                                    dateStart = (obj.getString("start_date"));
                                    dateEnd = (obj.getString("end_date"));
                                    hour_start = obj.getString("start_hr");
                                    hour_end = obj.getString("end_hr");
                                    min_start = obj.getString("start_min");
                                    min_end = obj.getString("end_min");
                                    String duration = obj.getString("duration");

                                    sessions.setSessionCoverImage(coverImage);
                                    sessions.setDate_start(dateStart);
                                    sessions.setDate_end(dateEnd);
                                    sessions.setHour_start(hour_start);
                                    sessions.setHour_end(hour_end);
                                    sessions.setMinute_start(min_start);
                                    sessions.setMinute_end(min_end);
                                    sessions.setSessDuration(duration);
                                    sessions.setSessionVideoLink(videoLink);
                                    sessions.setSessionName(sessionName);
                                    sessions.setProgramId(programId);
                                    sessions.setProgramName(programName);
                                    sessions.setProgramSessionId(programSessionId);
                                    sessions.setSessionDesc(desc);
                                    sessions.setSessionFocusPoints(focusPoints);
                                    sessionsList.add(sessions);

                                    adapter = new SessionsAdapter(getActivity(), sessionsList);
                                    sessionsRV.setAdapter(adapter);

                                    try {
                                        addEvents(sessionName, programName, dateStart, dateEnd,
                                                hour_start, hour_end, min_start, min_end);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    //saveSessionsInfo(programId, programName, sessionName, programSessionId, videoLink, coverImage, desc, focusPoints, hour_start);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void addEvents(String sessionName, String programName, String dateStart, String dateEnd, String hour_start, String hour_end, String min_start, String min_end) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = sdf.parse(dateStart);
        Date endDate;
//        Date endDate = sdf.parse(dateEnd);

        Log.d(TAG, "Hour Start in method:\t" + hour_start);

        int hourStart_int = DateUtils.getHour(hour_start);
        int hourEnd_int = DateUtils.getHour(hour_end);

        int minStart = DateUtils.getMinute(min_start);
        int minEnd = DateUtils.getMinute(min_end);

//        if (hourStart_int > hourEnd_int || (hourStart_int == hourEnd_int && minStart >= minEnd)){
//            throw new ParseException("Start Hour Greater Than End Hour", 402);
//        } else {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, hourStart_int);
        calendar.set(Calendar.MINUTE, minStart);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, hourEnd_int);
        calendar.set(Calendar.MINUTE, minEnd);
        endDate = calendar.getTime();

        event = new SessionMeeting(startDate, endDate, "A Session");
//        Intent intent = new Intent(getActivity(), CalendarViewActivity.class);
//        intent.putExtra(CalendarConstants.EVENT_TO_SEND, event);
//        getActivity().setResult(Activity.RESULT_OK, intent);
//        getActivity().finish();

//        }

    }


    private void saveSessionsInfo(String programId, String programName, String sessionName, String programSessionId, String videoLink, String coverImage, String desc, String focusPoints, String dateTime) {
        sessions.setProgramId(programId);
        sessions.setSessionName(sessionName);
        sessions.setProgramName(programName);
        sessions.setProgramSessionId(programSessionId);
        sessions.setSessionVideoLink(videoLink);
        sessions.setSessionCoverImage(coverImage);
        sessions.setSessionDesc(desc);
        sessions.setSessionFocusPoints(focusPoints);
        sessions.setHour_start(dateTime);
        sessions.setCoachId(coachId);

        sessions.save();

        sessionsList = getAllSessions();
        Log.d(TAG, "DB Sessions size:\t" + getAllSessions().size());
        adapter = new SessionsAdapter(getActivity(), sessionsList);
        sessionsRV.setAdapter(adapter);

    }

    // TODO: 6/27/2018 complete vid of day
    private void initVideoOfDay() {
        AndroidNetworking.post(Constants.VID_OF_DAY_URL)
                .setTag("Get Video of the Day")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Vid_day response:\t" + response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                videoURL = jsonObject.getString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        // TODO: 7/12/2018 Show Video from url

    }

}
