package com.first.hdz.litepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.first.hdz.litepal.bean.Song;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test() {

    }

    /**
     * 查询
     */
    private void search(int flag) {
        if (flag == 1) {
            Song song = LitePal.find(Song.class, 1);
        } else if (flag == 2) {
            List<Song> allSongs = LitePal.findAll(Song.class);
        } else {
            List<Song> songs = LitePal.where("name like ?", "月亮%").order("duration").find(Song.class);
        }
    }

    /**
     * 增加  同步保存
     */
    private void insert() {
        Song song = new Song();
        song.setName("月亮代表我的心");
        song.setDuration("03:36");
        song.setLastModified(System.currentTimeMillis());
        song.save();
    }

    /**
     * 更新  同步更新
     */
    private void update(int flag) {
        if (flag == 1) {
            Song song = LitePal.find(Song.class, 1);
            song.setLastModified(System.currentTimeMillis());
            song.save();
        } else {
            Song song = new Song();
            song.setLastModified(System.currentTimeMillis());
            song.updateAll("name=?", "月亮代表我的心");
        }
    }

    /**
     * 删除  同步删除
     */
    private void delete(int flag) {
        if (flag == 1) {
            LitePal.delete(Song.class, 1);
        } else {
            LitePal.deleteAll(Song.class, "duration>?", "20");
        }
    }


    private void syncAction() {
        LitePal.findAllAsync(Song.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {

            }
        });

        LitePal.findAsync(Song.class, 1).listen(new FindCallback() {
            @Override
            public <Song> void onFinish(Song s) {

            }
        });
        Song song = new Song();
        song.setLastModified(System.currentTimeMillis());
        song.setDuration("03:20");
        song.setName("月亮");
        song.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {

            }
        });
        //多数据库
        //新的数据库 1
        LitePalDB litePalDB = new LitePalDB("demo", 1);
        litePalDB.addClassName(Song.class.getName());
        LitePal.use(litePalDB);
        //新的数据库 2
        LitePalDB litePalDB1 = LitePalDB.fromDefault("newdb");
        LitePal.use(litePalDB1);

        //切换数据库
        LitePal.useDefault();

        //删除数据库
        LitePal.deleteDatabase("newdb");
    }


}
