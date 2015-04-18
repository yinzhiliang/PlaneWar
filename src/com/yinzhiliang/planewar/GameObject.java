package com.yinzhiliang.planewar;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author yinzhiliang
 * 
 */
public class GameObject {
	
	/* ��ǰ�ٶ� */
	protected int mSpeed;
	/* ��ֵ */
	protected int mScore;
	/* �˺� */
	protected int mHarm;
	/* �����X���� */
	protected float mObjectX;
	/* �����Y���� */
	protected float mObjectY;
	/* ����Ŀ�� */
	protected float mObjectWidth;
	/* ����ĸ߶� */
	protected float mObjectHeight;
	/* ��Ļ�Ŀ�� */
	protected float mScreenWidth;
	/* ��Ļ�ĸ߶� */
	protected float mScreenHeight;
	/* �ж��Ƿ��� */
	protected boolean mIsAlive;
	/* �ж��Ƿ�ը */
	protected boolean mIsExplosion;
	/* ���� */
	protected Paint mPaint;
	/* ��Դ */
	protected Resources mResources;

	/**
	 * ���캯��
	 */
	GameObject(Resources aResources) {
		mResources = aResources;
		mPaint = new Paint();
	}

	/**
	 * ������Ļ��С
	 * 
	 * @param aScreenWidth
	 *            ��Ļ��
	 * @param aScreenHeight
	 *            ��Ļ��
	 */
	public void setScreenSize(float aScreenWidth, float aScreenHeight) {
		mScreenWidth = aScreenWidth;
		mScreenHeight = aScreenHeight;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param aSpeedRate
	 *            �ٶȲ���
	 * @param aCenterX
	 *            ����X����
	 * @param aCenterY
	 *            ����Y����
	 */
	public void initial(int aSpeedRate, float aCenterX, float aCenterY) {
		mIsAlive = true;
		mObjectY = -mObjectHeight;
	}

	/**
	 * ��ʼ��ͼƬ��Դ
	 */
	public void initBitmap() {

	}

	/**
	 * �Ի�
	 * 
	 * @param canvas
	 *            Canvas
	 */
	public void drawSelf(Canvas canvas) {

	}

	/**
	 * �ͷ���Դ
	 */
	public void release() {

	}

	/**
	 * ִ������߼�
	 */
	public void logic() {

	}

	/**
	 * ������
	 * 
	 * @param harm
	 *            �˺�ֵ
	 */
	public void attacked(int harm) {

	}

	/**
	 * ����Ƿ�����ײ
	 * 
	 * @param aObject
	 *            ������
	 * @return �Ƿ�����ײ
	 */
	public boolean isCollide(GameObject aObject) {
		if (mObjectX <= aObject.getObjectX()
				&& mObjectX + mObjectWidth <= aObject.getObjectX() + 20) {
			return false;
		}
		// ����1λ�ھ���2���Ҳ�
		else if (aObject.getObjectX() <= mObjectX
				&& aObject.getObjectX() + aObject.getObjectWidth() <= mObjectX + 20) {
			return false;
		}
		// ����1λ�ھ���2���Ϸ�
		else if (mObjectY <= aObject.getObjectY()
				&& mObjectY + mObjectHeight <= aObject.getObjectY() + 50) {
			return false;
		}
		// ����1λ�ھ���2���·�
		else if (aObject.getObjectY() <= mObjectY
				&& aObject.getObjectY() + aObject.getObjectHeight() <= mObjectY + 10) {
			return false;
		}
		return true;
	}

	/**
	 * �����Ƿ���
	 * 
	 * @param aIsAlive
	 *            �Ƿ���
	 */
	public void setAlive(boolean aIsAlive) {
		this.mIsAlive = aIsAlive;
	}

	/**
	 * ��ȡ�Ƿ���
	 * 
	 * @return �Ƿ���
	 */
	public boolean isAlive() {
		return mIsAlive;
	}

	/**
	 * �����Ƿ�ը
	 * 
	 * @param aIsExplosion
	 *            �Ƿ�ը
	 */
	public void setExplosion(boolean aIsExplosion) {
		this.mIsExplosion = aIsExplosion;
	}

	/**
	 * ��ȡ�Ƿ�ը
	 * 
	 * @return �Ƿ�ը
	 */
	public boolean isExplosion() {
		return mIsExplosion;
	}

	/**
	 * ��ȡ�����X����
	 * 
	 * @return �����X����
	 */
	public float getObjectX() {
		return mObjectX;
	}

	/**
	 * ��ȡ�����Y����
	 * 
	 * @return �����Y����
	 */
	public float getObjectY() {
		return mObjectY;
	}

	/**
	 * ��ȡ������
	 * 
	 * @return ������
	 */
	public float getObjectWidth() {
		return mObjectWidth;
	}

	/**
	 * ��ȡ����߶�
	 * 
	 * @return ����߶�
	 */
	public float getObjectHeight() {
		return mObjectHeight;
	}

	/**
	 * ��ȡ�ö����ֵ
	 * 
	 * @return ��ֵ
	 */
	public int getScore() {
		return mScore;
	}
}
