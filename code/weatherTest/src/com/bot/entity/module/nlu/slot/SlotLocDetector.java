/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *@desc:λ�������ʶ�𣬵���ģʽ
 *@author �˕D
 *@date:2020��2��7������4:52:42
 */
public class SlotLocDetector { 
	private static SlotLocDetector locDet = new SlotLocDetector(); 
	private List<LOCNode> treeList = new ArrayList<LOCNode>();
	private static final String FILE_DIR = "F:/file_temp/";
	private static final String FILE_NAME = "�����б�.xls";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	
	public static final int INIT_AS_XIN_ZHI_TIAN_QI = 1;
	public static final int INIT_AS_JI_SU_SHU_JU = 2;
	/**
	 * 
	 */
	public SlotLocDetector() {
	}
	
	public void init(int initType) {
		switch(initType) {
		case INIT_AS_XIN_ZHI_TIAN_QI: initAsXinZhiTianQi();break;
		case INIT_AS_JI_SU_SHU_JU: initAsJiSuShuJu();break;
		}
	}
	
	public void initAsJiSuShuJu() {
		
	}
	
	public void initAsXinZhiTianQi() {
		LOCNode root = new LOCNode();
		root.setLeaf(false);
		root.setParentIndex(-1);
		root.setName("ROOT");
		root.setIndex(0);
		treeList.add(root);
		
        try {
            //��ȡϵͳ�ĵ�
            POIFSFileSystem fspoi=new POIFSFileSystem(new FileInputStream(FILE_PATH));
            //��������������
            HSSFWorkbook workbook=new HSSFWorkbook(fspoi);
            //�������������
            HSSFSheet sheet=workbook.getSheet("���ڳ���");
            for(int i = 1;i<sheet.getPhysicalNumberOfRows();i++) {
            	//�õ�Excel���
            	HSSFRow row = sheet.getRow(i);
            	//�õ�Excel������ָ���еĵ�Ԫ��
            	HSSFCell cell = row.getCell(2);
            	String[] splitList = cell.getStringCellValue().split("/");
            	int parentIndex = 0;
            	for(String loc : splitList) {
            		boolean flag = true;
            		int j = 0;
            		for(int index : treeList.get(parentIndex).getChildList()) {
            			if(treeList.get(index).getName().equals(loc)) {
            				parentIndex = treeList.get(index).getIndex();
            				flag = false;
            				if(j==splitList.length-1) {
            					treeList.get(index).setCode(row.getCell(0).getStringCellValue());
            				}
            				break;
            			}
            		}
            		//û�ҵ�
            		if(flag) {
            			//���ֻ��һ�����������ϸ�������������ۡ�����
            			if(splitList.length==1) {
            				String provinceName = row.getCell(1).getStringCellValue();
            				if(provinceName.charAt(provinceName.length()-1)=='ʡ') {
            					provinceName = provinceName.substring(0, provinceName.length());
            				}
            				LOCNode newFatherNode = new LOCNode();
            				newFatherNode.setName(provinceName);
            				newFatherNode.setIndex(treeList.size());
            				newFatherNode.setLeaf(false);
            				newFatherNode.setParentIndex(parentIndex);
            				
            				parentIndex = newFatherNode.getIndex();
            				treeList.add(newFatherNode);
            			}
            			LOCNode newNode = new LOCNode();
            			newNode.setName(loc);
            			newNode.setIndex(treeList.size());
            			newNode.setParentIndex(parentIndex);
            			if(j==splitList.length-1) {
        					newNode.setCode(row.getCell(0).getStringCellValue());
        				}
            			treeList.add(newNode);
            			treeList.get(parentIndex).getChildList().add(newNode.getIndex());
            			treeList.get(parentIndex).setLeaf(false);
            			parentIndex = newNode.getIndex();
            		}
            		j++;
            	}
            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static SlotLocDetector getInstance() {
		return locDet;
	}
	/***
	 * 
	 *@desc:�ص�ʵ��ʶ��
	 *@param utterance
	 *@return
	 *@return:LOCNode
	 *@trhows
	 */
	public LOCNode start(String utterance) {
		/*
		 *1. �ҵ������ض�Ӧ���
		 *2. δ�ҵ������ظ��ڵ�
		 */
		LOCNode node = null;
		for(LOCNode n: treeList) {
			if(n.isLeaf() && utterance.indexOf(n.getName())!=-1) {
				node = new LOCNode(n);
				break;
			}
		}
		if(node==null) {
			node = new LOCNode(treeList.get(0));
		}
		
		return node;
	}
}
