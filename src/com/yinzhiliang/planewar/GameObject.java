package com.yinzhiliang.planewar;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author yinzhiliang
 * 
 */
public class GameObject {
	
	/* 当前速度 */
	protected int mSpeed;
	/* 分值 */
	protected int mScore;
	/* 伤害 */
	protected int mHarm;
	/* 对象的X坐标 */
	protected float mObjectX;
	/* 对象的Y坐标 */
	protected float mObjectY;
	/* 对象的宽度 */
	protected float mObjectWidth;
	/* 对象的高度 */
	protected float mObjectHeight;
	/* 屏幕的宽度 */
	protected float mScreenWidth;
	/* 屏幕的高度 */
	protected float mScreenHeight;
	/* 判断是否存活 */
	protected boolean mIsAlive;
	/* 判断是否爆炸 */
	protected boolean mIsExplosion;
	/* 画笔 */
	protected Paint mPaint;
	/* 资源 */
	protected Resources mResources;

	/**
	 * 构造函数
	 */
	GameObject(Resources aResources) {
		mResources = aResources;
		mPaint = new Paint();
	}

	/**
	 * 设置屏幕大小
	 * 
	 * @param aScreenWidth
	 *            屏幕宽
	 * @param aScreenHeight
	 *            屏幕高
	 */
	public void setScreenSize(float aScreenWidth, float aScreenHeight) {
		mScreenWidth = aScreenWidth;
		mScreenHeight = aScreenHeight;
	}

	/**
	 * 初始化
	 * 
	 * @param aSpeedRate
	 *            速度参数
	 * @param aCenterX
	 *            中心X坐标
	 * @param aCenterY
	 *            中心Y坐标
	 */
	public void initial(int aSpeedRate, float aCenterX, float aCenterY) {
		mIsAlive = true;
		mObjectY = -mObjectHeight;
	}

	/**
	 * 初始化图片资源
	 */
	public void initBitmap() {

	}

	/**
	 * 自绘
	 * 
	 * @param canvas
	 *            Canvas
	 */
	public void drawSelf(Canvas canvas) {

	}

	/**
	 * 释放资源
	 */
	public void release() {

	}

	/**
	 * 执行相关逻辑
	 */
	public void logic() {

	}

	/**
	 * 被攻击
	 * 
	 * @param harm
	 *            伤害值
	 */
	public void attacked(int harm) {

	}

	/**
	 * 检测是否发生碰撞
	 * 
	 * @param aObject
	 *            检测对象
	 * @return 是否发生碰撞
	 */
	public boolean isCollide(GameObject aObject) {
		if (mObjectX <= aObject.getObjectX()
				&& mObjectX + mObjectWidth <= aObject.getObjectX() + 20) {
			return false;
		}
		// 矩形1位于矩形2的右侧
		else if (aObject.getObjectX() <= mObjectX
				&& aObject.getObjectX() + aObject.getObjectWidth() <= mObjectX + 20) {
			return false;
		}
		// 矩形1位于矩形2的上方
		else if (mObjectY <= aObject.getObjectY()
				&& mObjectY + mObjectHeight <= aObject.getObjectY() + 50) {
			return false;
		}
		// 矩形1位于矩形2的下方
		else if (aObject.getObjectY() <= mObjectY
				&& aObject.getObjectY() + aObject.getObjectHeight() <= mObjectY + 10) {
			return false;
		}
		return true;
	}

	/**
	 * 设置是否存活
	 * 
	 * @param aIsAlive
	 *            是否存活
	 */
	public void setAlive(boolean aIsAlive) {
		this.mIsAlive = aIsAlive;
	}

	/**
	 * 获取是否存活
	 * 
	 * @return 是否存活
	 */
	public boolean isAlive() {
		return mIsAlive;
	}

	/**
	 * 设置是否爆炸
	 * 
	 * @param aIsExplosion
	 *            是否爆炸
	 */
	public void setExplosion(boolean aIsExplosion) {
		this.mIsExplosion = aIsExplosion;
	}

	/**
	 * 获取是否爆炸
	 * 
	 * @return 是否爆炸
	 */
	public boolean isExplosion() {
		return mIsExplosion;
	}

	/**
	 * 获取对象的X坐标
	 * 
	 * @return 对象的X坐标
	 */
	public float getObjectX() {
		return mObjectX;
	}

	/**
	 * 获取对象的Y坐标
	 * 
	 * @return 对象的Y坐标
	 */
	public float getObjectY() {
		return mObjectY;
	}

	/**
	 * 获取对象宽度
	 * 
	 * @return 对象宽度
	 */
	public float getObjectWidth() {
		return mObjectWidth;
	}

	/**
	 * 获取对象高度
	 * 
	 * @return 对象高度
	 */
	public float getObjectHeight() {
		return mObjectHeight;
	}

	/**
	 * 获取该对象分值
	 * 
	 * @return 分值
	 */
	public int getScore() {
		return mScore;
	}
}
