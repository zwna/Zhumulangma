package com.gykj.zhumulangma.common.event;

/**
 * Description: <EventCode><br>
 * Author:      mxdl<br>
 * Date:        2018/4/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface EventCode {
    interface Main {
        int JPUSH = 1000;
        int NAVIGATE=1001;
        int HIDE_GP=1002;
        int SHOW_GP=1003;
        int LOGIN=1004;
        int LOGINSUCC=1005;
        int SHARE=1006;
        //1000开始
    }

    interface Listen {
        int DOWNLOAD_SORT=2000;
        //2000开始
    }

    interface FindCode {
        //3000开始
    }

    interface MeCode {
        //4000开始
        int NEWS_TYPE_ADD = 4000;
        int NEWS_TYPE_DELETE = 4001;
        int NEWS_TYPE_UPDATE = 4002;
        int NEWS_TYPE_QUERY = 4003;

        int news_detail_add  = 4004;
    }
}
