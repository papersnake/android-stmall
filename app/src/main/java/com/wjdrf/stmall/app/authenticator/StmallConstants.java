package com.wjdrf.stmall.app.authenticator;

/**
 * Created by papersnake on 14-3-11.
 */
public interface StmallConstants {
    String TAG = "OAuthSTmall";

    String CREDENTIALS_STORE_PREF_FILE = "oauth_stmall";

    public static final String CLIENT_ID = "1000000001";

    public static final String CLIENT_SECRET = "2000000002";

    public static final String AUTHORIZATION_CODE_SERVER_URL = "http://stmall.sinaapp.com/Api/Oauth/authorize";

    public static final String AUTHORIZATION_IMPLICIT_SERVER_URL = "http://stmall.sinaapp.com/Api/Oauth/authorize";

    public static final String TOKEN_SERVER_URL = "http://stmall.sinaapp.com/Api/Oauth/access_token";

    public static final String REDIRECT_URL = "http://localhost/Callback";

    //private StmallConstants() {
    //}

    public static class Extra {
        private Extra() {}

        public static final String GOOD_ITEM = "good_item";
        public static final String SEARCH_KEY = "good_search_key";
        public static final String GOOD_OBJ = "good_obj";

    }

    public static class Action {
        private Action() {}

        public static final String ACTION_GOOD_VIEW = "com.wjdrf.stmall.app.GOOD_VIEW";
    }
}
