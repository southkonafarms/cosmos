package com.zenred.cosmos.domain;

/**
 * basic Rename object
 * useful for generating JSON
 * @author johnredden
 *
 */

public class BasicRename extends Rename {
	
	public BasicRename(){
		super();
	}
	
	public BasicRename(RenameObjectType renameObjectType, String genericName, String renameName, Integer renameCount){
		super.setRenameObjectType(renameObjectType);
		super.setGenericName(genericName);
		super.setRenameName(renameName);
		super.setRenameCount(renameCount);
	}

	public RenameObjectType getRenameObjectType() {
		return super.getRenameObjectType();
	}
	public void setRenameObjectType(RenameObjectType renameObjectType) {
		super.setRenameObjectType(renameObjectType); 
	}
	public String getGenericName() {
		return super.getGenericName();
	}
	public void setGenericName(String genericName) {
		super.setGenericName(genericName);
	}
	public String getRenameName() {
		return super.getRenameName();
	}
	public void setRenameName(String renameName) {
		super.setRenameName(renameName);
	}
	public Integer getRenameCount() {
		return super.getRenameCount();
	}
	public void setRenameCount(Integer renameCount) {
		super.setRenameCount(renameCount);
	}

}
