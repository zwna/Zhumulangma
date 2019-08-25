package com.gykj.zhumulangma.listen.mvvm.model;

import android.app.Application;
import android.app.ListActivity;
import android.database.Cursor;

import com.google.gson.Gson;
import com.gykj.zhumulangma.common.App;
import com.gykj.zhumulangma.common.bean.PlayHistoryBean;
import com.gykj.zhumulangma.common.dao.PlayHistoryBeanDao;
import com.gykj.zhumulangma.common.mvvm.model.ZhumulangmaModel;
import com.gykj.zhumulangma.common.net.http.RxAdapter;
import com.gykj.zhumulangma.common.util.log.TLog;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class HistoryModel extends ZhumulangmaModel {

    public HistoryModel(Application application) {
        super(application);
    }

    public Observable<List<PlayHistoryBean>> getHistory(int page,int pagesize) {
        return io.reactivex.Observable.create((ObservableOnSubscribe<List<PlayHistoryBean>>) emitter -> {
            List<PlayHistoryBean> list = new ArrayList<>();
            String sql = "SELECT * FROM "+ PlayHistoryBeanDao.TABLENAME+
                    " GROUP BY "+PlayHistoryBeanDao.Properties.AlbumId.columnName+
                    "  ORDER BY "+PlayHistoryBeanDao.Properties.Datatime.columnName+
                    " DESC LIMIT ? OFFSET ?";
            try (Cursor c = App.getDaoSession().getDatabase().rawQuery(sql,
                    new String[]{
                            String.valueOf(pagesize),
                            String.valueOf((page-1)*20),
                    })) {
                if (c.moveToFirst()) {
                    do {

                        list.add(new PlayHistoryBean(
                                c.getLong(c.getColumnIndex(PlayHistoryBeanDao.Properties.SoundId.columnName)),
                                c.getLong(c.getColumnIndex(PlayHistoryBeanDao.Properties.AlbumId.columnName)),
                                c.getString(c.getColumnIndex(PlayHistoryBeanDao.Properties.Kind.columnName)),
                                c.getInt(c.getColumnIndex(PlayHistoryBeanDao.Properties.Percent.columnName)),
                                c.getLong(c.getColumnIndex(PlayHistoryBeanDao.Properties.Datatime.columnName)),
                                new Gson().fromJson(c.getString(c.getColumnIndex(PlayHistoryBeanDao.Properties.Track.columnName)), Track.class)
                        ));
                    } while (c.moveToNext());
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onNext(list);
            emitter.onComplete();
        }).compose(RxAdapter.schedulersTransformer());
    }
}