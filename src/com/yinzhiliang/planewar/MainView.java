package com.yinzhiliang.planewar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author yinzhiliang
 * 
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {

	/* Canvas */
	private Canvas mCanvas;
	/* Paint */
	private Paint mPaint;
	/* SurfaceHolder */
	private SurfaceHolder mSurfaceHolder;
	/* mThread */
	private Thread mThread;
	/* mMyPlane */
	private MyPlane mMyPlane;
	/* mScoreSum */
	private int mScoreSum;
	/* mEnemyCount */
	private int mEnemyCount;
	/* mScreenWidth */
	private float mScreenWidth;
	/* mScreenHeight */
	private float mScreenHeight;
	/* mThreadFlag */
	private boolean mThreadFlag;
	/* mIsTouch */
	private boolean mIsTouch;
	/* mEnemys */
	private List<GameObject> mEnemys;
	/* MainActivity */
	private MainActivity mActivity;
	/* Handler */
	private Handler mMyHandler;

	/**
	 * 构造函数
	 * @param context Context
	 */
	public MainView(Context context) {
		super(context);
		mActivity = (MainActivity) context;
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		mPaint = new Paint();
		mEnemys = new ArrayList<GameObject>();
		mMyPlane = new MyPlane(this, getResources());
		for (int i = 0; i < 8; i++) {
			Enemy enemy = new Enemy(getResources());
			mEnemys.add(enemy);
		}
		mEnemyCount = 0;
		mThread = new Thread(this);
		mMyHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					mActivity.finish();
				}
			}
		};
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mScreenWidth = this.getWidth();
		mScreenHeight = this.getHeight();
		for (GameObject obj : mEnemys) {
			obj.setScreenSize(mScreenWidth, mScreenHeight);
		}
		mMyPlane.setScreenSize(mScreenWidth, mScreenHeight);
		mMyPlane.setAlive(true);
		mThreadFlag = true;
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThreadFlag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			mIsTouch = false;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			if (x > mMyPlane.getObjectX()
					&& x < mMyPlane.getObjectX() + mMyPlane.getObjectWidth()
					&& y > mMyPlane.getObjectY()
					&& y < mMyPlane.getObjectY() + mMyPlane.getObjectHeight()) {
				mIsTouch = true;
				return true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE
				&& event.getPointerCount() == 1) {
			if (mIsTouch) {
				float x = event.getX();
				float y = event.getY();
				if (x > mMyPlane.getCenterX() + 20) {
					if (mMyPlane.getCenterX() + mMyPlane.getSpeed() <= mScreenWidth) {
						mMyPlane.setCenterX(mMyPlane.getCenterX()
								+ mMyPlane.getSpeed());
					}
				} else if (x < mMyPlane.getCenterX() - 20) {
					if (mMyPlane.getCenterX() - mMyPlane.getSpeed() >= 0) {
						mMyPlane.setCenterX(mMyPlane.getCenterX()
								- mMyPlane.getSpeed());
					}
				}
				if (y > mMyPlane.getCenterY() + 20) {
					if (mMyPlane.getCenterY() + mMyPlane.getSpeed() <= mScreenHeight) {
						mMyPlane.setCenterY(mMyPlane.getCenterY()
								+ mMyPlane.getSpeed());
					}
				} else if (y < mMyPlane.getCenterY() - 20) {
					if (mMyPlane.getCenterY() - mMyPlane.getSpeed() >= 0) {
						mMyPlane.setCenterY(mMyPlane.getCenterY()
								- mMyPlane.getSpeed());
					}
				}
			}
		}
		return false;
	}

	/**
	 * 初始化各对象
	 */
	public void initObject() {
		for (GameObject obj : mEnemys) {
			if (obj instanceof Enemy) {
				if (!obj.isAlive()) {
					obj.initial(8, 0, 0);
					mEnemyCount++;
					if (mEnemyCount >= 8) {
						mEnemyCount = 0;
					}
					break;
				}
			}
		}
		mMyPlane.initButtle();
	}

	/**
	 * 自绘
	 */
	public void drawSelf() {
		try {
			mCanvas = mSurfaceHolder.lockCanvas();
			mCanvas.drawColor(Color.LTGRAY);
			for (GameObject obj : mEnemys) {
				if (obj.isAlive()) {
					obj.drawSelf(mCanvas);
					if (!obj.isExplosion() && mMyPlane.isAlive()) {
						if (obj.isCollide(mMyPlane)) {
							mMyPlane.setAlive(false);
						}
					}
				}
			}
			if (!mMyPlane.isAlive()) {
				mThreadFlag = false;
			}
			mMyPlane.drawSelf(mCanvas);
			mMyPlane.shoot(mCanvas, mEnemys);
			mPaint.setTextSize(30);
			mPaint.setColor(Color.rgb(235, 161, 1));
			mCanvas.drawText("积分:" + String.valueOf(mScoreSum), 30, 40, mPaint);
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (mCanvas != null)
				mSurfaceHolder.unlockCanvasAndPost(mCanvas);
		}
	}

	/**
	 * 释放资源
	 */
	public void release() {
		for (GameObject obj : mEnemys) {
			obj.release();
		}
		mMyPlane.release();
	}

	@Override
	public void run() {
		while (mThreadFlag) {
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mMyHandler.sendEmptyMessage(1);
	}

	/**
	 * 增加分值
	 * @param aScore 分值
	 */
	public void addScoreSum(int aScore) {
		mScoreSum += aScore;
	}
}
