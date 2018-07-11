package com.jjoey.sportseco.utils;

public class Constants {

    public static final String VID_URL = "https://www.youtube.com/watch?v=fI-XfjQCDwM";

    public static final String BASE_URL = "http://devsports.copycon.in/api";

    public static final String SUCCESS_MSG = "success";

    public static final String LOGIN_COACH = BASE_URL + "/coach_login";

    public static final String COACH_BATCH_LIST = BASE_URL + "/coach_batch_list/";

    public static final String COACH_PROGRAM_LIST = BASE_URL + "/coach_program_list/";

    public static final String SESSION_LIST = BASE_URL + "/coach_program_session_list"; // coach_id, batch_id, prg_id, prg_user_map_id (int)

    public static final String COACH_PLAYER_LIST = BASE_URL + "/coach_player_list"; // coach_id, academy_id, batch_id, prg_user_map_id, prg_session_id (int)

    public static final String PLAYER_ATTENDANCE = BASE_URL + "/program_player_attendance";
    //prg_user_map_id, prg_session_id, batch_id, coach_id (int), player_id (str arr), att_status (str_arr)

    public static final String UPLOAD_PLAYER_IMAGE = BASE_URL + "/player_image_upload"; //file name: player_image

    public static final String UPLOAD_PLAYER_VIDEO = BASE_URL + "/player_video_upload"; //file name: player_video

    public static final String COACH_PLAYER_FEEDBACK = BASE_URL + "/coach_player_feedback";

}
