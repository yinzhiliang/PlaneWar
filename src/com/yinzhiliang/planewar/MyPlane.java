package com.yinzhiliang.planewar;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class MyPlane extends GameObject {
	
	/* 飞机图片 */
	private Bitmap mMyPlane;
	/* 尾部火焰图片 */
    private Bitmap mPlaneFire;
	/* 中心X坐标*/
	private float mCenterX;
	/* 中心Y坐标 */
	private float mCenterY;
	/* 火焰高度 */
	private int mFireHeight;
	/* 发射出去的子弹 */
	private List<GameObject> mBullets;
	/* 主界面 */
	private MainView mMainView;

	/**
	 * 构造函数
	 * @param aMainView 主界面
	 * @param aResources 资源
	 */
	MyPlane(MainView aMainView, Resources aResources) {
		super(aResources);
		initBitmap();
		this.mSpeed = 8;
		this.mMainView = aMainView;
		mBullets = new ArrayList<GameObject>();
		for (int i = 0; i < 4; i++) {
			Bullet bullet = new Bullet(aResources);
			mBullets.add(bullet);
		}
	}

	@Override
	public void setScreenSize(float aScreenWidth, float aScreenHeight) {
		super.setScreenSize(aScreenWidth, aScreenHeight);
		mObjectX = aScreenWidth / 2 - mObjectWidth / 2;
		mObjectY = aScreenHeight - mObjectHeight;
		mCenterX = mObjectX + mObjectWidth / 2;
		mCenterY = mObjectY + mObjectHeight / 2;
	}

	@Override
	public void initBitmap() {
		mMyPlane = BitmapFactory.decodeResource(mResources, R.drawable.plane);
        mPlaneFire = BitmapFactory.decodeResource(mResources, R.drawable.plane_rear_fire);
		mObjectWidth = mMyPlane.getWidth();
		mObjectHeight = mMyPlane.getHeight();
        mFireHeight = mPlaneFire.getHeight();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (mIsAlive) {
			canvas.save();
			canvas.drawBitmap(mMyPlane, mObjectX, mObjectY - mFireHeight, mPaint);
			canvas.drawBitmap(mPlaneFire, mObjectX, mObjectY - mFireHeight + mObjectHeight, mPaint);
			canvas.restore();
		}
	}

	/**
	 * 初始化子弹
	 */
	public void initButtle() {
		for (GameObject obj : mBullets) {
			if (!obj.isAlive()) {
				obj.initial(20, mCenterX, mCenterY);
				break;
			}
		}
	}

	/**
	 * 射击
	 * @param canvas Canvas
	 * @param planes 飞机
	 */
	public void shoot(Canvas canvas, List<GameObject> mPlanes) {
		for (GameObject obj : mBullets) {
			if (obj.isAlive()) {
				for (GameObject pobj : mPlanes) {
					if (pobj.isAlive() && !pobj.isExplosion()) {
						if (obj.isCollide(pobj)) {
							pobj.attacked(obj.mHarm);
							if (pobj.isExplosion()) {
								mMainView.addScoreSum(pobj.getScore());
							}
							break;
						}
					}
				}
				obj.drawSelf(canvas);
			}
		}
	}

	@Override
	public void release() {
		for (GameObject obj : mBullets) {
			obj.release();
		}
		if (mMyPlane != null && !mMyPlane.isRecycled()) {
			mMyPlane.recycle();
		}
	}

	/**
	 * 获取mSpeed
	 * @return mSpeed
	 */
	public int getSpeed() {
		return mSpeed;
	}

	/**
	 * 获取中心X坐标
	 * @return 中心X坐标
	 */
	public float getCenterX() {
		return mCenterX;
	}

	/**
	 * 设置中心X坐标
	 * @param aCenterX 中心X坐标
	 */
	public void setCenterX(float aCenterX) {
		this.mCenterX = aCenterX;
		this.mObjectX = aCenterX - mObjectWidth / 2;
	}

	/**
	 * 获取中心Y坐标
	 * @return 中心Y坐标
	 */
	public float getCenterY() {
		return mCenterY;
	}

	/**
	 * 设置中心Y坐标
	 * @param aCenterY 中心Y坐标
	 */
	public void setCenterY(float aCenterY) {
		this.mCenterY = aCenterY;
		this.mObjectY = aCenterY - mObjectHeight / 2;
	}
}
