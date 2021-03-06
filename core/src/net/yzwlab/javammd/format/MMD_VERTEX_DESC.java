package net.yzwlab.javammd.format;

import java.io.Serializable;

import net.yzwlab.javammd.model.MMDBone;

public class MMD_VERTEX_DESC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final MMD_VERTEX_TEXUSE original;

	public final MMD_VERTEX_TEXUSE faced;

	public final MMD_VERTEX_TEXUSE current;

	/**
	 * ボーンの一覧を保持します。
	 */
	protected MMDBone[] bones;

	protected float bweight;

	public MMD_VERTEX_DESC(PMD_VERTEX_RECORD vert) {
		assert vert != null;
		this.original = new MMD_VERTEX_TEXUSE();
		this.faced = new MMD_VERTEX_TEXUSE();
		this.current = new MMD_VERTEX_TEXUSE();
		this.bones = null;
		this.bweight = 0.0f;

		this.original.point.x = vert.x;
		this.original.point.y = vert.y;
		this.original.point.z = vert.z;
		this.original.normal.x = vert.nx;
		this.original.normal.y = vert.ny;
		this.original.normal.z = vert.nz;
		this.original.uv.x = vert.tx;
		this.original.uv.y = vert.ty;
	}

	/**
	 * 内容の妥当性を検証します。
	 */
	public void verify() {
		if (bones == null) {
			System.err.println("NG");
		}
	}

	public void setCurrent(MMD_VERTEX_TEXUSE current) {
		assert current != null;
		this.current.copyFrom(current);
	}

	public void copyCurrentTo(MMD_VERTEX_TEXUSE target) {
		assert target != null;
		target.copyFrom(current);
	}

	public MMD_VERTEX_TEXUSE getOriginal() {
		MMD_VERTEX_TEXUSE r = new MMD_VERTEX_TEXUSE();
		r.copyFrom(original);
		return r;
	}

	public MMD_VERTEX_TEXUSE getFaced(MMD_VERTEX_TEXUSE buffer) {
		assert buffer != null;
		buffer.copyFrom(faced);
		return buffer;
	}

	public void setFaced(MMD_VERTEX_TEXUSE current) {
		assert current != null;
		this.faced.copyFrom(current);
	}

	public void setFaced(PMD_MORP_VERTEX_RECORD v) {
		assert v != null;
		float[] values = v.vec;
		MMD_VECTOR3 point = faced.point;
		point.x = (values[0]);
		point.y = (values[1]);
		point.z = (values[2]);
	}

	public void setFaced(MMD_VECTOR3 v) {
		assert v != null;
		faced.point.copyFrom(v);
	}

	public MMDBone[] getBones() {
		return bones;
	}

	public void setBones(MMDBone[] bones) {
		assert bones != null;
		this.bones = bones;
	}

	public float getBweight() {
		return bweight;
	}

	public void setBweight(float bweight) {
		this.bweight = bweight;
	}

}
