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
public class Bullet extends GameObject {

	/* 子弹图片 */
	private Bitmap mBullet;

	/**
	 * 构造函数
	 * @param aResources 资源
	 */
	Bullet(Resources aResources) {
		super(aResources);
		initBitmap();
	}

	@Override
	public void initial(int aSpeedRate, float aCenterX, float aCenterY) {
		mIsAlive = true;
		mHarm = 1;
		Random ran = new Random();
		mSpeed = ran.nextInt(aSpeedRate) + 100;
		mObjectX = aCenterX - mObjectWidth / 2;
		mObjectY = aCenterY - mObjectHeight;
	}

	@Override
	public void initBitmap() {
		mBullet = BitmapFactory.decodeResource(mResources, R.drawable.bullet);
		if (mBullet != null) {
			mObjectWidth = mBullet.getWidth();
			mObjectHeight = mBullet.getHeight();
		}
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (mIsAlive) {
			canvas.save();
			canvas.clipRect(mObjectX, mObjectY, mObjectX + mObjectWidth,
					mObjectY + mObjectHeight);
			canvas.drawBitmap(mBullet, mObjectX, mObjectY, mPaint);
			canvas.restore();
			logic();
		}
	}

	@Override
	public void release() {
		if (mBullet != null && !mBullet.isRecycled()) {
			mBullet.recycle();
		}
	}

	@Override
	public void logic() {
		if (mObjectY >= 0) {
			mObjectY -= mSpeed;
		} else {
			mIsAlive = false;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		if (obj instanceof Enemy) {
			if (mObjectX <= obj.getObjectX()
					&& mObjectX + mObjectWidth <= obj.getObjectX()) {
				return false;
			}
			// 矩形1位于矩形2的右侧
			else if (obj.getObjectX() <= mObjectX
					&& obj.getObjectX() + obj.getObjectWidth() <= mObjectX) {
				return false;
			}
			// 矩形1位于矩形2的上方
			else if (mObjectY <= obj.getObjectY()
					&& mObjectY + mObjectHeight + 30 <= obj.getObjectY()) {
				return false;
			}
			// 矩形1位于矩形2的下方
			else if (obj.getObjectY() <= mObjectY
					&& obj.getObjectY() + obj.getObjectHeight() + 30 <= mObjectY) {
				return false;
			}
		}
		mIsAlive = false;
		return true;
	}
}
