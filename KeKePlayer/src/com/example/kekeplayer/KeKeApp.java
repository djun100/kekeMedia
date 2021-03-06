package com.example.kekeplayer;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.example.kekeplayer.type.ChannelInfo;
import com.example.kekeplayer.type.FocusImageInfo;
import com.example.kekeplayer.utils.AppUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class KeKeApp extends Application {
	public static final int COLLECT_MAX = 15;
	public static String DEFAULT_SDCARD_PATH = "/mnt/sdcard";
	public static final int HISTORY_MAX = 15;
	public static final int REMIND_MAX = 15;
	public static final int SUBSCIBE_MAX = 15;
	public static final String uri = "http://api.cbox.cntv.cn/api/";
	public static Context context;
	public static Handler handler;
	private static List<ChannelInfo> mChannelInfoList;
	public static String mCurDate = null;
	public static int mCurrentPosition;
	public static float mDisplayScale;
	public static int mDuration;
	public static List<FocusImageInfo> mList;
	private static String mVersion;
	private static Date now = null;
	public static long uiThreadID;

	public static List<ChannelInfo> getChannelInfoList() {
		return mChannelInfoList;
	}

	public static Date getNow() {
		return now;
	}

	private String getSDPath() {
		File sdDir = null;
		String path="/mnt/sdcard/KeKePlayer/";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			path = String.valueOf(sdDir.toString())+"/KeKePlayer/";
		}
		return path;
	}

	public static String getVersion() {
		return mVersion;
	}

	public static void setChannelInfoList(List<ChannelInfo> paramList) {
		mChannelInfoList = paramList;
	}

	public static void setNow(Date paramDate) {
		now = paramDate;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
//		}
		initImageLoader(getApplicationContext());
		context = getBaseContext();
		handler = new Handler();
		uiThreadID = Thread.currentThread().getId();
		DEFAULT_SDCARD_PATH = getSDPath();
		mVersion = AppUtils.getVersionName(getApplicationContext());
		Log.e("tst", ":"+mVersion);
		mDisplayScale = context.getResources().getDisplayMetrics().density;
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

}
