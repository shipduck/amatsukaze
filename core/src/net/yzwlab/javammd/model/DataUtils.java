package net.yzwlab.javammd.model;

import java.util.Arrays;

/**
 * データ関係のユーティリティクラスです。
 * 데이터 관계 유틸리티 클래스입니다
 */
public class DataUtils {

	/**
	 * バイトデータのオフセット部分を取得します。
	 * 바이트 데이터의 오프셋 부분을 가져옵니다
	 * 
	 * @param data
	 *            データ。nullは不可。
	 * @param offset
	 *            オフセットの長さ。
	 * @return バイトデータ。
	 */
	public static byte[] offsetBytes(byte[] data, int offset) {
		assert data != null;
		byte[] ret = new byte[data.length - offset];
		for (int i = offset; i < data.length; i++) {
			ret[i - offset] = data[i];
		}
		return ret;
	}

	/**
	 * 文字列を取得します。
	 * 문자열을 가져옵니다
	 * 
	 * @param data
	 *            バイト列。nullは不可。
	 * @return 文字列。
	 */
	public static byte[] getStringData(byte[] data) {
		assert data != null;
		int len = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0) {
				len = i;
				break;
			}
		}
		byte[] ret = new byte[len];
		for (int i = 0; i < len; i++) {
			ret[i] = data[i];
		}
		return ret;
	}

	/**
	 * 文字列を取得します。
	 * 문자열을 가져옵니다.
	 * 
	 * @param data
	 *            バイト列。nullは不可。바이트. null는 불가.
	 * @param maxLen
	 *            最大長。
	 * @return 文字列。
	 */
	public static byte[] getStringData(byte[] data, int maxLen) {
		assert data != null;
		int len = maxLen;
		for (int i = 0; i < data.length && i < maxLen; i++) {
			if (data[i] == 0) {
				len = i;
				break;
			}
		}
		byte[] ret = new byte[len];
		for (int i = 0; i < len; i++) {
			ret[i] = data[i];
		}
		return ret;
	}

	/**
	 * 文字列を比較します。
	 * 문자열을 비교합니다
	 * 
	 * @param data1
	 *            バイト列。nullは不可。
	 * @param data2
	 *            バイト列。nullは不可。
	 * @param maxLen
	 *            最大長。
	 * @return 文字列。
	 */
	public static int compare(byte[] data1, byte[] data2, int maxLen) {
		assert data1 != null;
		assert data2 != null;
		return compare(getStringData(data1, maxLen),
				getStringData(data2, maxLen));
	}

	/**
	 * 文字列を比較します。
	 * 문자열을 비교합니다
	 * 
	 * @param data1
	 *            バイト列。nullは不可。
	 * @param data2
	 *            バイト列。nullは不可。
	 * @return 文字列。
	 */
	public static int compare(byte[] data1, byte[] data2) {
		assert data1 != null;
		assert data2 != null;
		if (Arrays.equals(data1, data2)) {
			return 0;
		}
		for (int i = 0; i < data1.length && i < data2.length; i++) {
			byte d1 = data1[i];
			byte d2 = data2[i];
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return +1;
			}
		}
		if (data1.length < data2.length) {
			return -1;
		}
		if (data1.length > data2.length) {
			return +1;
		}
		return 0;
	}

	/**
	 * インスタンス化を許しません。
	 */
	private DataUtils() {
		;
	}

}
