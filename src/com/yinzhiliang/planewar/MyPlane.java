package com.yinzhiliang.planewar;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class MyPlane extends GameObject {
	
	/* �ɻ�ͼƬ */
	private Bitmap mMyPlane;
	/* β������ͼƬ */
    private Bitmap mPlaneFire;
	/* ����X����*/
	private float mCenterX;
	/* ����Y���� */
	private float mCenterY;
	/* ����߶� */
	private int mFireHeight;
	/* �����ȥ���ӵ� */
	private List<GameObject> mBullets;
	/* ������ */
	private MainView mMainView;

	/**
	 * ���캯��
	 * @param aMainView ������
	 * @param aResources ��Դ
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
	 * ��ʼ���ӵ�
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
	 * ���
	 * @param canvas Canvas
	 * @param planes �ɻ�
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
	 * ��ȡmSpeed
	 * @return mSpeed
	 */
	public int getSpeed() {
		return mSpeed;
	}

	/**
	 * ��ȡ����X����
	 * @return ����X����
	 */
	public float getCenterX() {
		return mCenterX;
	}

	/**
	 * ��������X����
	 * @param aCenterX ����X����
	 */
	public void setCenterX(float aCenterX) {
		this.mCenterX = aCenterX;
		this.mObjectX = aCenterX - mObjectWidth / 2;
	}

	/**
	 * ��ȡ����Y����
	 * @return ����Y����
	 */
	public float getCenterY() {
		return mCenterY;
	}

	/**
	 * ��������Y����
	 * @param aCenterY ����Y����
	 */
	public void setCenterY(float aCenterY) {
		this.mCenterY = aCenterY;
		this.mObjectY = aCenterY - mObjectHeight / 2;
	}
}
