package com.yinzhiliang.planewar;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * @author yinzhiliang
 * 
 */
public class Enemy extends GameObject {
	
	/* 飞机图片 */
	private Bitmap mEnemy;
	/* 血量 */
	private int mBlood;
	
	/**
	 * 构造函数
	 * @param aResources 资源
	 */
	Enemy(Resources aResources) {
		super(aResources);
		initBitmap();
		this.mScore = 100;
	}

	@Override
	public void initial(int aSpeedRate, float aCenterX, float aCenterY) {
		super.initial(aSpeedRate, aCenterX, aCenterY);
		mBlood = 1;
		Random ran = new Random();
		mObjectX = ran.nextInt((int) (mScreenWidth - mObjectWidth));
		this.mSpeed = ran.nextInt(aSpeedRate);
	}

	@Override
	public void initBitmap() {
		mEnemy = BitmapFactory.decodeResource(mResources, R.drawable.enemy);
		mObjectWidth = mEnemy.getWidth();
		mObjectHeight = mEnemy.getHeight();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (mIsAlive) {
			if (!mIsExplosion) {
				canvas.save();
				canvas.clipRect(mObjectX, mObjectY, mObjectX + mObjectWidth,
						mObjectY + mObjectHeight);
				canvas.drawBitmap(mEnemy, mObjectX, mObjectY, mPaint);
				canvas.restore();
				logic();
			} else {
				canvas.save();
				canvas.clipRect(mObjectX, mObjectY, mObjectX + mObjectWidth,
						mObjectY + mObjectHeight);
				canvas.drawBitmap(mEnemy, mObjectX, mObjectY, mPaint);
				canvas.restore();
				mIsExplosion = false;
				mIsAlive = false;
			}
		}
	}

	@Override
	public void release() {
		if (mEnemy != null && !mEnemy.isRecycled()) {
			mEnemy.recycle();
		}
	}

	@Override
	public void logic() {
		if (mObjectY < mScreenHeight) {
			mObjectY += mSpeed;
		} else {
			mIsAlive = false;
		}
	}

	@Override
	public void attacked(int harm) {
		mBlood -= harm;
		if (mBlood <= 0) {
			mIsExplosion = true;
		}
	}
}
