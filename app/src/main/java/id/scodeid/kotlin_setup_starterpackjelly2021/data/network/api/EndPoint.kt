package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.api

import id.scodeid.kotlin_setup_starterpackjelly2021.BuildConfig


class EndPoint {

    companion object {
        private const val SERVER = BuildConfig.BASE_URL
//        private const val SERVER = BuildConfig.BASE_URL_ONLINE
//        const val UPLOAD_FOTO = BuildConfig.BASE_URL_UPLOAD_FOTO
//        const val PATCH = BuildConfig.BASE_URL_PATCH_FOTO

        /**
         * @param
         * authentication
         */
        const val AUTH_REGISTER = "${SERVER}api/auth/signup"
        const val GET_SCORE = "${SERVER}api/score"
//        const val AUTH_LOGIN = SERVER+"logindroid"
//        const val ATUH_FORGOT_SEND_TOKEN = SERVER+"reset"
//        const val ATUH_FORGOT_UPDATE = SERVER+"reset/password"
    }
}