package com.zenred.cosmos.domain;

public enum RenameObjectLoadType {
	
	RENAMEOBJECTTYPE ("RenameObjectLoadType"){
		@Override
		public String getName() {
			return "renameObjectType";
		}

		@Override
		public void storeValue(String value) {
			dbxferObject = new DBxferObject();
			rename = new Rename();
			dbxferObject.setType(rename);
			rename = (Rename)dbxferObject.getType();
			String renameObjectType = value;
			rename.renameObjectType = RenameObjectType.valueOf(renameObjectType);
			dbxferList = DBxferList.instance();
			
		}
	},
	GENERICNAME ("genericName"){
		@Override
		public String getName() {
			return "genericName";
		}

		@Override
		public void storeValue(String value) {
			String genericName = value;
			rename.setGenericName(genericName);
		}
	},
	RENAMENAME ("renameName"){

		@Override
		public String getName() {
			return "renameName";
		}

		@Override
		public void storeValue(String value) {
			String renameName = value;
			rename.setRenameName(renameName);
		}
	},
	RENAMECOUNT ("renameCount"){

		@Override
		public String getName() {
			return "renameCount";
		}

		@Override
		public void storeValue(String value) {
			Integer renameCount = new Integer(value);
			rename.setRenameCount(renameCount);
			dbxferObject.setType(rename);
			dbxferList.addDomainObject(dbxferObject);
		}
	}
	;
	
	private String type;
	private RenameObjectLoadType (String type){
		
	}
	
	public abstract String getName();
	public abstract void storeValue(String value);
	
	private static DBxferObject dbxferObject;
	private static DBxferList dbxferList;
	private static Rename rename;


}

