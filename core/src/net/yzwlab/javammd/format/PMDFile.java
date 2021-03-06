package net.yzwlab.javammd.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.yzwlab.javammd.IReadBuffer;
import net.yzwlab.javammd.ReadException;

public class PMDFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PMD_HEADER m_header;

	protected List<PMD_VERTEX_RECORD> m_vertexs;

	protected List<Short> m_indexs;

	protected List<PMD_MATERIAL_RECORD> m_materials;

	protected List<PMD_BONE_RECORD> m_bones;

	protected List<PMD_IK_RECORD> m_ikbones;

	protected List<PMD_MORP_RECORD> m_morps;

	protected List<Short> m_ctrls;

	protected List<PMD_GRP_NAME_RECORD> m_grp_name;

	protected List<PMD_GRP_RECORD> m_grp;

	protected List<PMD_RIGID_BODY_RECORD> m_rigidBodies;

	protected List<PMD_CONSTRAINT_RECORD> m_constraints;

	public PMDFile() {
		this.m_constraints = new ArrayList<PMD_CONSTRAINT_RECORD>();
		this.m_rigidBodies = new ArrayList<PMD_RIGID_BODY_RECORD>();
		this.m_grp = new ArrayList<PMD_GRP_RECORD>();
		this.m_grp_name = new ArrayList<PMD_GRP_NAME_RECORD>();
		this.m_ctrls = new ArrayList<Short>();
		this.m_morps = new ArrayList<PMD_MORP_RECORD>();
		this.m_ikbones = new ArrayList<PMD_IK_RECORD>();
		this.m_bones = new ArrayList<PMD_BONE_RECORD>();
		this.m_materials = new ArrayList<PMD_MATERIAL_RECORD>();
		this.m_indexs = new ArrayList<Short>();
		this.m_vertexs = new ArrayList<PMD_VERTEX_RECORD>();
		this.m_header = new PMD_HEADER();
	}

	public void dispose() {
	}

	/**
	 * バッファからデータを開きます。
	 * 
	 * @param fs
	 *            ファイル。nullは不可。
	 * @return データ。
	 * @throws ReadException
	 *             読み込み関係のエラー。
	 */
	public boolean open(IReadBuffer fs) throws ReadException {
		assert fs != null;
		int size = 0;
		byte size_b = 0;
		short size_w = 0;
		m_header = PMD_Reader.read(fs, m_header);
		size = fs.readInteger();
		SetVertexChunkSize(size);
		if (size != 0)
			for (int ai = 0; ai < size; ai++) {
				GetVertexChunk().get(ai).read(fs);
			}
		;
		size = fs.readInteger();
		SetIndexChunkSize(size);
		if (size != 0)
			for (int ai = 0; ai < size; ai++) {
				GetIndexChunk().set(ai, fs.readShort());
			}
		;
		size = fs.readInteger();
		SetMaterialChunkSize(size);
		if (size != 0)
			for (int ai = 0; ai < size; ai++) {
				GetMaterialChunk().get(ai).read(fs);
			}
		;
		size_w = fs.readShort();
		SetBoneChunkSize(size_w);
		if (size_w != 0)
			for (int ai = 0; ai < size_w; ai++) {
				GetBoneChunk().get(ai).read(fs);
			}
		;
		size_w = fs.readShort();
		SetIKChunkSize(size_w);
		for (int i = 0; i < size_w; i++) {
			GetIKChunk().get(i).read(fs);
		}
		size_w = fs.readShort();
		SetMorpChunkSize(size_w);
		for (int i = 0; i < size_w; i++) {
			GetMorpChunk().get(i).read(fs);
		}
		size_b = fs.readByte();
		SetCtrlChunkSize(size_b);
		if (size_b != 0)
			for (int ai = 0; ai < size_b; ai++) {
				GetCtrlChunk().set(ai, fs.readShort());
			}
		;
		size_b = fs.readByte();
		SetGrpNameChunkSize(size_b);
		if (size_b != 0)
			for (int ai = 0; ai < size_b; ai++) {
				GetGrpNameChunk().get(ai).read(fs);
			}
		;
		size = fs.readInteger();
		SetGrpChunkSize(size);
		if (size != 0)
			for (int ai = 0; ai < size; ai++) {
				GetGrpChunk().get(ai).read(fs);
			}
		;
		if (fs.isEOF()) {
			return true;
		}
		size_b = fs.readByte();
		if (size_b != 0) {
			fs.readByteArray(276);
			for (int i = 0; i < m_bones.size(); i++) {
				fs.readByteArray(20);
			}
			Iterator<PMD_MORP_RECORD> it = m_morps.iterator();
			if (it.hasNext()) {
				it.next();
				for (; it.hasNext(); it.next()) {
					fs.readByteArray(20);
				}
			}
			for (Iterator<PMD_GRP_NAME_RECORD> git = m_grp_name.iterator(); git
					.hasNext(); git.next()) {
				fs.readByteArray(50);
			}
		}
		if (fs.isEOF()) {
			return true;
		}
		for (int i = 0; i < 10; i++) {
			fs.readByteArray(100);
		}
		size = fs.readInteger();
		SetRigidBodyChunkSize(size);
		if (size != 0) {
			for (int ai = 0; ai < size; ai++) {
				GetRigidBodyChunk().get(ai).read(fs);
			}
			;
		}
		size = fs.readInteger();
		SetConstraintChunkSize(size);
		if (size != 0) {
			for (int ai = 0; ai < size; ai++) {
				GetConstraintChunk().get(ai).read(fs);
			}
			;
		}
		return true;
	}

	public int GetVersion() {
		return 0;
	}

	public void SetVersion(int ver) {
	}

	public int GetVertexChunkSize() {
		return m_vertexs.size();
	}

	public void SetVertexChunkSize(int size) {
		m_vertexs = new ArrayList<PMD_VERTEX_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_vertexs.add(new PMD_VERTEX_RECORD());
		}
	}

	public List<PMD_VERTEX_RECORD> GetVertexChunk() {
		return m_vertexs;
	}

	public int GetIndexChunkSize() {
		return m_indexs.size();
	}

	public void SetIndexChunkSize(int size) {
		m_indexs = new ArrayList<Short>(size);
		for (int ai = 0; ai < size; ai++) {
			m_indexs.add(new Short((short) 0));
		}
	}

	public List<Short> GetIndexChunk() {
		return m_indexs;
	}

	public int GetBoneChunkSize() {
		return m_bones.size();
	}

	public void SetBoneChunkSize(int size) {
		m_bones = new ArrayList<PMD_BONE_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_bones.add(new PMD_BONE_RECORD());
		}
	}

	public List<PMD_BONE_RECORD> GetBoneChunk() {
		return m_bones;
	}

	public int GetIKChunkSize() {
		return m_ikbones.size();
	}

	public void SetIKChunkSize(int size) {
		m_ikbones = new ArrayList<PMD_IK_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_ikbones.add(new PMD_IK_RECORD());
		}
	}

	public List<PMD_IK_RECORD> GetIKChunk() {
		return m_ikbones;
	}

	public int GetMaterialChunkSize() {
		return m_materials.size();
	}

	public void SetMaterialChunkSize(int size) {
		m_materials = new ArrayList<PMD_MATERIAL_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_materials.add(new PMD_MATERIAL_RECORD());
		}
	}

	public List<PMD_MATERIAL_RECORD> GetMaterialChunk() {
		return m_materials;
	}

	public int GetMorpChunkSize() {
		return m_morps.size();
	}

	public void SetMorpChunkSize(int size) {
		m_morps = new ArrayList<PMD_MORP_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_morps.add(new PMD_MORP_RECORD());
		}
	}

	public List<PMD_MORP_RECORD> GetMorpChunk() {
		return m_morps;
	}

	public int GetCtrlChunkSize() {
		return m_ctrls.size();
	}

	public void SetCtrlChunkSize(int size) {
		m_ctrls = new ArrayList<Short>(size);
		for (int ai = 0; ai < size; ai++) {
			m_ctrls.add(new Short((short) 0));
		}
	}

	public List<Short> GetCtrlChunk() {
		return m_ctrls;
	}

	public int GetGrpNameChunkSize() {
		return m_grp_name.size();
	}

	public void SetGrpNameChunkSize(int size) {
		m_grp_name = new ArrayList<PMD_GRP_NAME_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_grp_name.add(new PMD_GRP_NAME_RECORD());
		}
	}

	public List<PMD_GRP_NAME_RECORD> GetGrpNameChunk() {
		return m_grp_name;
	}

	public int GetGrpChunkSize() {
		return m_grp.size();
	}

	public void SetGrpChunkSize(int size) {
		m_grp = new ArrayList<PMD_GRP_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_grp.add(new PMD_GRP_RECORD());
		}
	}

	public List<PMD_GRP_RECORD> GetGrpChunk() {
		return m_grp;
	}

	public int GetRigidBodyChunkSize() {
		return m_rigidBodies.size();
	}

	public void SetRigidBodyChunkSize(int size) {
		m_rigidBodies = new ArrayList<PMD_RIGID_BODY_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_rigidBodies.add(new PMD_RIGID_BODY_RECORD());
		}
	}

	public List<PMD_RIGID_BODY_RECORD> GetRigidBodyChunk() {
		return m_rigidBodies;
	}

	public int GetConstraintChunkSize() {
		return m_constraints.size();
	}

	public void SetConstraintChunkSize(int size) {
		m_constraints = new ArrayList<PMD_CONSTRAINT_RECORD>(size);
		for (int ai = 0; ai < size; ai++) {
			m_constraints.add(new PMD_CONSTRAINT_RECORD());
		}
	}

	public List<PMD_CONSTRAINT_RECORD> GetConstraintChunk() {
		return m_constraints;
	}

}
